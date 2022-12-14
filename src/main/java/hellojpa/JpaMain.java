package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            /*Member member = new Member();

            member.setId(2L);
            member.setName("HelloB");*/
            /*Member findMember = em.find(Member.class, 1L);
            findMember.setName("HelloJPA");*/

            //비영속 ->jpa랑 관련 x 엔티티를 생성한 상태
           /* Member member = new Member();
            member.setId(100L);
            member.setName("HelloJPA");

            //엔티티를 영속
            System.out.println("====BEFORE====");
            em.persist(member);
            System.out.println("====AFTER====");*/

            //저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team); //persist -> 항상 pk값이 들어감 영속 상태가 되면 pk값 세팅 후 들어간다.

            Member member = new Member();
            member.setName("member1");
            member.changeTeam(team);//** 연관관계메서드
            em.persist(member);

            //team.getMembers().add(member);//**

            em.flush();
            em.clear();

            //조회
            Team findTeam = em.find(Team.class, team.getId());
            List<Member> members = findTeam.getMembers();


            for (Member m : members) {
                System.out.println("m.getName() = " + m.getName());
            }

            /*//수정
            Team newTeam = em.find(Team.class, 10L);
            findMember.setTeam(newTeam);*/
            
            tx.commit(); //커밋시점에 db에 쿼리가 날라감

        } catch (Exception e) {
          tx.rollback();
        } finally {
            em.close();
        }



        emf.close();


    }
}
