package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/validation/v1/items")
@RequiredArgsConstructor
public class ValidationItemControllerV1 {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v1/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v1/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v1/addForm";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, RedirectAttributes redirectAttributes,Model model) {
        Map<String,String> errors = new HashMap<>();
        if (!StringUtils.hasText(item.getItemName())) {
            errors.put("itemName","상품 이름은 필 수 입니다.");
        }
        if (item.getPrice()==null) {
            errors.put("price","상품 가격은 필수 입니다.");
        }else if ( item.getPrice()<1000||item.getPrice()>10000000) {
            errors.put("price","가격은 1,000원에서 1,000,000 원까지 허용됩니다.");
        }

        if (item.getQuantity()==null) {
            errors.put("quantity","상품 수량은 필수 입니다.");
        }else if(item.getQuantity()>=1000 || item.getQuantity()<=0){
            errors.put("quantity","수량은 1개이상 1000개 미만으로 입력해주세요");
        }

        if(item.getPrice()* item.getQuantity()<10000){
            long total = item.getPrice() * item.getQuantity();
            errors.put("message","최소 주문금액은 10,000원입니다. 현재 값 = \" " + total +" \"");
        }

        //검증 실패시 다시 입력 폼으로
        if(!errors.isEmpty()){
            model.addAttribute("errors",errors);
            System.out.println("errors = " + errors);
            return "validation/v1/addForm";
        }


        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v1/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v1/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/v1/items/{itemId}";
    }

}

