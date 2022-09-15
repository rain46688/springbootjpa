package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    // 아이템 저장하기
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    // 기존에 방법은 등록이건 수정이건 saveItem을 호출하고 내부에서 if(item.getId() == null) 즉 아이디 값이 없으면
    // 있으면 수정이고 없으면 등록인것이라 이렇게 구분해서 persist 할지 merge를 할지를 나눈것
    // 하지만 merge는 사용할때 주의해야되는게 엔티티에 값이 없는 프로퍼티들은 다 null로 해서 들어감 싹 디비가 갈아쳐버리는다는것
    // 패스워드는 변경안하고 싶어서 안넣고 보낸건데 null로 그냥 들어가버릴수있다는거라 위험하다.

    // 그렇기에 수정할때는 updateItem를 따로 만들고 호출해서 itemRepository.findOne(itemId)를 하고 객체를 호출하면
    // 이건 jpa에 의해 관리가 되는 영속성 객체이다. 영속성 객체는 set으로 값을 변경만 해도 따로 업데이트 문을 날리지 않고 (변경 감지 == 더티 체킹)
    // 바로 알아서 업데이트를 jpa가 해준다. 이렇게 하는게 바람직하다고 함
    
    // 아이템 수정 용도로 개선한 코드
    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity){
        Item findItem = itemRepository.findOne(itemId);
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
    }

    // 아이템 조회하기
    public Item findItem(Long id){
        return itemRepository.findOne(id);
    }

    // 아이템들 조회하기
    public List<Item> findItems(){
        return itemRepository.findAll();
    }
}
