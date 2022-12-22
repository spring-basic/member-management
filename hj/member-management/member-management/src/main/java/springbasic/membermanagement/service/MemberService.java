package springbasic.membermanagement.service;

import springbasic.membermanagement.domain.Member;
import springbasic.membermanagement.repository.MemberRepository;
import springbasic.membermanagement.repository.MemoryMemberRepository;

import  java.util.List;
import  java.util.Optional;

public class MemberService {
    private final MemberRepository memberReopsitory = new MemoryMemberRepository();

    /**
    *회원 가입
     */
    public Long join(Member member){
        validateDuplicateMember(member); //중복 회원 검증
        memberReopsitory.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member){
        memberReopsitory.findByName(member.getName()).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }

    /**
    *전체 최원 조회
     */
    public List<Member> findMembers(){
        return memberReopsitory.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberReopsitory.findById(memberId);
    }
}
