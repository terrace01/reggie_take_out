package cn.luxun.reggie.service.impl;

import cn.luxun.reggie.common.EmployeeThreadLocal;
import cn.luxun.reggie.common.Result;
import cn.luxun.reggie.entity.AddressBook;
import cn.luxun.reggie.mapper.AddressBookMapper;
import cn.luxun.reggie.service.AddressBookService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {

	@Override
	public Result<AddressBook> saveAddressBookByParams(AddressBook addressBook) {
		addressBook.setUserId(EmployeeThreadLocal.getCurrentId());
		this.save(addressBook);
		return Result.success(addressBook);
	}

	@Override
	public Result<AddressBook> setDefaultAddressBook(AddressBook addressBook) {
		LambdaUpdateWrapper<AddressBook> wrapper = new LambdaUpdateWrapper<>();
		wrapper.eq(AddressBook::getUserId, EmployeeThreadLocal.getCurrentId());
		wrapper.set(AddressBook::getIsDefault, 0);
		//SQL:update address_book set is_default = 0 where user_id = ?
		this.update(wrapper);

		addressBook.setIsDefault(1);
		//SQL:update address_book set is_default = 1 where id = ?
		this.updateById(addressBook);
		return Result.success(addressBook);
	}

	@Override
	public Result getAddressBookById(Long id) {
		AddressBook addressBook = this.getById(id);
		if (addressBook != null) {
			return Result.success(addressBook);
		} else {
			return Result.error("没有找到该对象");
		}
	}

	@Override
	public Result<AddressBook> getDefaultAddressBook() {

		LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(AddressBook::getUserId, EmployeeThreadLocal.getCurrentId());
		queryWrapper.eq(AddressBook::getIsDefault, 1);

		//SQL:select * from address_book where user_id = ? and is_default = 1
		AddressBook addressBook = this.getOne(queryWrapper);

		if (null == addressBook) {
			return Result.error("没有找到该对象");
		} else {
			return Result.success(addressBook);
		}

	}

	@Override
	public Result<List<AddressBook>> getAllAddressBookById(AddressBook addressBook) {
		addressBook.setUserId(EmployeeThreadLocal.getCurrentId());

		//条件构造器
		LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(null != addressBook.getUserId(), AddressBook::getUserId, addressBook.getUserId());
		queryWrapper.orderByDesc(AddressBook::getUpdateTime);

		//SQL:select * from address_book where user_id = ? order by update_time desc
		return Result.success(this.list(queryWrapper));
	}
}
