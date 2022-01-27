package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/validation/v2/items")
@RequiredArgsConstructor
public class ValidationItemControllerV2 {

    private final ItemRepository itemRepository;
    private final ItemValidator itemValidator;

    @InitBinder
    public void init(WebDataBinder dataBinder){
        dataBinder.addValidators(itemValidator)/**/;
    }

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v2/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v2/addForm";
    }
/*
    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {


        if (!StringUtils.hasText(item.getItemName())) {
            bindingResult.addError(new FieldError("item","itemName","상품이름은 필수 입니다."));
        }

        if(item.getQuantity()==null || item.getQuantity()>=1000 || item.getQuantity()<=0){
            bindingResult.addError(new FieldError("item","quantity","수량은 1개이상 1000개 미만으로 입력해주세요"));
        }
        if(item.getPrice()!=null&&item.getQuantity()!=null){
            int result = item.getPrice()*item.getQuantity();
            if(result < 10000){
                bindingResult.addError(new ObjectError("item","최소 주문금액은 10,000원입니다. 현재 값 = "+result));
            }
        }else{
            if(item.getPrice()==null) {
                bindingResult.addError(new FieldError("item", "price", "가격은 필수입니다. "));
            }
            if(item.getQuantity()==null) {
                bindingResult.addError(new FieldError("item", "quantity", "수량은 필수입니다. "));
            }
        }
*/

        /*@PostMapping("/add")
        public String addItemV2(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {


            if (!StringUtils.hasText(item.getItemName())) {
                bindingResult.addError(new FieldError("item","itemName",item.getItemName(),false,null,null,"상품이름은 필수 입니다."));
            }


            if(item.getPrice()!=null&&item.getQuantity()!=null){
                int result = item.getPrice()*item.getQuantity();
                if(result < 10000){
                    bindingResult.addError(new ObjectError("item","최소 주문금액은 10,000원입니다. 현재 값 = "+result));
                }
                if(item.getPrice()<1000||item.getPrice()>1000000){
                    bindingResult.addError(new FieldError("item", "price",item.getPrice(),false,null,null, "가격은 1,000 ~ 1,000,000 까지 허용됩니다."));
                }
                if(item.getQuantity()<=0||item.getQuantity()>100){
                    bindingResult.addError(new FieldError("item", "quantity",item.getQuantity(),false,null,null, "수량은  1 ~ 100 까지 허용됩니다."));
                }
            }else{
                if(item.getPrice()==null) {
                    bindingResult.addError(new FieldError("item", "price",item.getPrice(),false,null,null, "가격은 필수입니다. "));
                }
                if(item.getQuantity()==null) {
                    bindingResult.addError(new FieldError("item", "quantity",item.getQuantity(),false,null,null, "수량은 필수입니다. "));
                }
            }
*/
        /*@PostMapping("/add")
        public String addItemV3(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {


            if (!StringUtils.hasText(item.getItemName())) {
                bindingResult.addError(new FieldError("item","itemName",item.getItemName(),false,new String[]{"required.item.itemName"},null,null));
            }


            if(item.getPrice()!=null&&item.getQuantity()!=null){
                int result = item.getPrice()*item.getQuantity();
                if(result < 10000){
                    bindingResult.addError(new ObjectError("item",new String[]{"totalPriceMin"},new Object[]{"10000",result},null));
                }
                if(item.getPrice()<1000||item.getPrice()>1000000){
                    bindingResult.addError(new FieldError("item", "price",item.getPrice(),false,new String[]{"range.item.price"},new Object[]{1000,100000},null));
                }
                if(item.getQuantity()<=0||item.getQuantity()>100){
                    bindingResult.addError(new FieldError("item", "quantity",item.getQuantity(),false,new String[]{"max.item.quantity"},new Object[]{1,100},null));
                }
            }else{
                if(item.getPrice()==null) {
                    bindingResult.addError(new FieldError("item", "price",item.getPrice(),false,new String[]{"required.item.price"},null,null));
                }
                if(item.getQuantity()==null) {
                    bindingResult.addError(new FieldError("item", "quantity",item.getQuantity(),false,new String[]{"required.item.quantity"},null,null));
                }
            }
*/
/*
    @PostMapping("/add")
    public String addItemV4(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {


        if (!StringUtils.hasText(item.getItemName())) {
            bindingResult.rejectValue("itemName","required");
        }


        if(item.getPrice()!=null&&item.getQuantity()!=null){
            int result = item.getPrice()*item.getQuantity();
            if(result < 10000){
                bindingResult.reject("totalPriceMin",new Object[]{10000,result},null);
            }
            if(item.getPrice()<1000||item.getPrice()>1000000){
                bindingResult.rejectValue("price","range", new Object[]{1000,1000000},null);
            }
            if(item.getQuantity()<=0||item.getQuantity()>100){
                bindingResult.rejectValue("quantity","max", new Object[]{0,100},null);
            }
        }else{
            if(item.getPrice()==null) {
                bindingResult.rejectValue("price","required");
            }
            if(item.getQuantity()==null) {
                bindingResult.rejectValue("quantity","required");
            }
        }

        //검증 실패시 다시 입력 폼으로
        if(bindingResult.hasErrors()){
            log.info("errors = " + bindingResult);
            return "validation/v2/addForm";
        }


        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }*/
/*
    @PostMapping("/add")
    public String addItemV5(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        itemValidator.validate(item,bindingResult);

        //검증 실패시 다시 입력 폼으로
        if(bindingResult.hasErrors()){
            log.info("errors = " + bindingResult);
            return "validation/v2/addForm";
        }


        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }*/

    @PostMapping("/add")
    public String addItemV6(@Validated @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        //검증 실패시 다시 입력 폼으로
        if(bindingResult.hasErrors()){
            log.info("errors = " + bindingResult);
            return "validation/v2/addForm";
        }

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/v2/items/{itemId}";
    }

}