package study.datajpa.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

@SpringBootTest
@Transactional
@Rollback(false)
public class MemberTest {

    @PersistenceContext
    EntityManager em;

    @Test
    public void testEntity(){

        Team teamA = Team.builder()
                .name("teamA")
                .build();

        Team teamB = Team.builder()
                .name("teamB")
                .build();

        em.persist(teamA);
        em.persist(teamB);

        System.out.println("teamA = " + teamA);
        System.out.println("teamB = " + teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        em.flush(); //강제로 insert query를 실행
        em.clear(); //영속성컨텍스트 캐시 초기화

        //깔끔하게 조회를 위해 위 초기화 작업
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();

        for (Member member : members) {
            System.out.println("member = " + member);
            System.out.println("-> member.team = " + member.getTeam());
        }

    }

}