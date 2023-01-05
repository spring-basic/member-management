package spring.basicspring.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spring.basicspring.domain.Member;
import spring.basicspring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void  beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);

    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("hello");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

    }

    @Test
    void findMembers() {

        Member member1 = new Member();
        member1.setName("member1");
        memberService.join(member1);

        Member member2 = new Member();
        member2.setName("member2");
        memberService.join(member2);

        List<Member> result = memberService.findMembers();

        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void findOne() {
        //given
        Member member1 = new Member();
        member1.setName("member1");
        memberService.join(member1);

        Member member2 = new Member();
        member2.setName("member2");
        memberService.join(member2);

        Long findId = member2.getId();

        //when
        Optional<Member> result = memberService.findOne(findId);

        //then
        Assertions.assertThat(result.get()).isEqualTo(member2);


    }

    @Test
    void findFault() {
        //given
        Member member1 = new Member();
        member1.setName("member1");
        memberService.join(member1);

        Member member2 = new Member();
        member2.setName("member2");
        memberService.join(member2);

        Long findId = 5L;

        //when
        Optional<Member> result = memberService.findOne(findId);

        //then
        Assertions.assertThat(result.isEmpty()).isEqualTo(true);
    }
}