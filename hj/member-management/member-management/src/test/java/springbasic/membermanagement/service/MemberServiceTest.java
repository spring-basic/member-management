package springbasic.membermanagement.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import springbasic.membermanagement.domain.Member;
import springbasic.membermanagement.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach(){
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("hello");

        //when
        Long savedId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(savedId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("hyeonjin");

        Member member2 = new Member();
        member2.setName("hyeonjin");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        //then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

    @Test
    void 멤버_전체_조회() {
        //given
        Member member1 = new Member();
        member1.setName("hyeonjin");
        memberService.join(member1);

        Member member2 = new Member();
        member2.setName("sejin");
        memberService.join(member2);

        Member member3 = new Member();
        member3.setName("youngjin");
        memberService.join(member3);

        //when
        List<Member> result = memberService.findMembers();

        //then
        assertThat(result.size()).isEqualTo(3);
    }

    @Test
    void 멤버_조회_성공() {
        //given
        Member member1 = new Member();
        member1.setName("hyeonjin");
        memberService.join(member1);

        Member member2 = new Member();
        member2.setName("sejin");
        memberService.join(member2);

        Member member3 = new Member();
        member3.setName("youngjin");
        Long findId = memberService.join(member3);

        //when
        Optional<Member> result = memberService.findOne(findId);

        //then
        assertThat(result.get()).isEqualTo(member3);
    }

    @Test
    void 멤버_조회_실패() {
        //given
        Member member1 = new Member();
        member1.setName("hyeonjin");
        memberService.join(member1);

        Member member2 = new Member();
        member2.setName("sejin");
        memberService.join(member2);

        Member member3 = new Member();
        member3.setName("youngjin");
        memberService.join(member3);

        Long findId = 5L;

        //when
        Optional<Member> result = memberService.findOne(findId);

        //then
        assertThat(result.isEmpty()).isEqualTo(true);
    }
}