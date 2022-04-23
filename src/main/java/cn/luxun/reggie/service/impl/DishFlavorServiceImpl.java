package cn.luxun.reggie.service.impl;

import cn.luxun.reggie.entity.DishFlavor;
import cn.luxun.reggie.mapper.DishFlavorMapper;
import cn.luxun.reggie.service.DishFlavorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
