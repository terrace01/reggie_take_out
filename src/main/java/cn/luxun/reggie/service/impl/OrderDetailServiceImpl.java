package cn.luxun.reggie.service.impl;

import cn.luxun.reggie.entity.OrderDetail;
import cn.luxun.reggie.mapper.OrderDetailMapper;
import cn.luxun.reggie.service.OrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
