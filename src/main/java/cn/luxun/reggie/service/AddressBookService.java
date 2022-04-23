package cn.luxun.reggie.service;

import cn.luxun.reggie.common.Result;
import cn.luxun.reggie.entity.AddressBook;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface AddressBookService extends IService<AddressBook> {

	/**
	 * 新增用户地址
	 *
	 * @param addressBook
	 * @return
	 */
	Result<AddressBook> saveAddressBookByParams(AddressBook addressBook);


	/**
	 * 设置默认地址
	 *
	 * @param addressBook
	 * @return
	 */
	Result<AddressBook> setDefaultAddressBook(AddressBook addressBook);

	/**
	 * 根据id查询地址
	 *
	 * @param id
	 * @return
	 */
	Result getAddressBookById(Long id);

	/**
	 * 查询默认地址
	 *
	 * @return
	 */
	Result<AddressBook> getDefaultAddressBook();

	/**
	 * 查询指定用户的全部地址
	 *
	 * @param addressBook
	 * @return
	 */
	Result<List<AddressBook>> getAllAddressBookById(AddressBook addressBook);
}
