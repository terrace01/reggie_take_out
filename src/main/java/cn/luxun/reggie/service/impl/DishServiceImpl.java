package cn.luxun.reggie.service.impl;


import cn.luxun.reggie.entity.Dish;
import cn.luxun.reggie.mapper.DishMapper;
import cn.luxun.reggie.service.DishService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {


}
