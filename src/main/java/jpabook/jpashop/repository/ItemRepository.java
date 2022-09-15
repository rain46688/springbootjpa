package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    // 아이템 등록
    // 이부분 Member랑 다르게 if문으로 나눈 이유는 나중에 설명해준다함
    // merge는 사용안하는게 좋다
    public void save(Item item){
       // if(item.getId() == null){
            em.persist(item);
//        }else{
//            em.merge(item);
//        }
    }

    // 아이템 하나 찾기
    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    // 아이템 여러개 찾기
    public List<Item> findAll(){
        return em.createQuery("select i from Item i",Item.class).getResultList();
    }

}
