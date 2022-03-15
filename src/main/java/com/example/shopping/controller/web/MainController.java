package com.example.shopping.controller.web;

import com.example.shopping.Entity.*;
import com.example.shopping.service.ActivityService;
import com.example.shopping.service.CategoryService;
import com.example.shopping.service.FavoriteService;
import com.example.shopping.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private ActivityService activityService;

    @RequestMapping("/main")
    public String toMain(){
        return "main";
    }

    /**
     * 导航栏搜索功能
     * @return
     */
    @RequestMapping("/search")
    public String toSearch(@RequestParam("goodsName")String goodsName, Model model, HttpSession session){

        if(goodsName==null){
            return "redirect:/main";
        }

        //获取用户的信息
        User user = (User) session.getAttribute("user");

        //将传回的信息存入Map
        Map<String,Object> map=new HashMap<>();

        //查询商品信息
        Goods goods = goodsService.findAllByName(goodsName);

        if(goods==null){
            return "redirect:/main";
        }

        //查询商品类别
        Integer category1 = goods.getCategory();
        Category category=categoryService.findAllById(category1);

        //查询商品图片
        List<String> imagepaths=goodsService.findImagePath(goods.getGoodsid());

        //优惠活动
        Activity activity=activityService.findById(goods.getActivityid());
        goods.setActivity(activity);

        //判断是否收藏
        if(user==null){
            //用户没有登录
            goods.setFav(false);
        }else {
            System.out.println(goods.getGoodsid());
            Integer favorite= favoriteService.findById(new FavoriteKey(user.getUserId(),goods.getGoodsid()));
            if(favorite==0){
                //没收藏
                goods.setFav(false);
            }else {
                goods.setFav(true);
            }
        }

        map.put("goods",goods);
        map.put("cate",category);
        map.put("image",imagepaths);
        model.addAttribute("goodsInfo",map);
        model.addAttribute("imagepaths",imagepaths);

        return "detail";

    }
}
