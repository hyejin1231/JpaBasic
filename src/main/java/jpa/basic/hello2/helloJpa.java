package jpa.basic.hello2;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class helloJpa {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            // 비영속 상태
            Member member = new Member();
//            member.setId(1L);
            member.setUsername("userA");

            // 영속 상태 (영속성 컨텍스트를 통해 객체 관리됨)
            em.persist(member);

           // 조회 -> 1차 캐시로 인해 select 쿼리문 나가지 않는다.
            Member findMember = em.find(Member.class, 1L);
            System.out.println("findMember.getId() = " + findMember.getId());
            System.out.println("findMember.getName() = " + findMember.getUsername());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();
    }
}
