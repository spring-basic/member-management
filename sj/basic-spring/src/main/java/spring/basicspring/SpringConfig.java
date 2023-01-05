package spring.basicspring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.basicspring.repository.MemberRepository;
import spring.basicspring.repository.MemoryMemberRepository;
import spring.basicspring.service.MemberService;

@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}
