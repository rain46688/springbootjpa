package jpabook.jpashop.service;

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

    // 아이템 조회하기
    public Item findItem(Long id){
        return itemRepository.findOne(id);
    }

    // 아이템들 조회하기
    public List<Item> findItems(){
        return itemRepository.findAll();
    }
}
