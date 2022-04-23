package cn.luxun.reggie.controller;

import cn.luxun.reggie.common.EmployeeThreadLocal;
import cn.luxun.reggie.common.Result;
import cn.luxun.reggie.entity.AddressBook;
import cn.luxun.reggie.service.AddressBookService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 地址簿管理
 */
@Slf4j
@RestController
@RequestMapping("/addressBook")
public class AddressBookController {

	@Autowired
	private AddressBookService addressBookService;

	/**
	 * 新增用户地址
	 *
	 * @param addressBook
	 * @return
	 */
	@PostMapping
	public Result<AddressBook> saveAddressBookByParams(@RequestBody AddressBook addressBook) {
		log.info("addressBook:{}", addressBook);
		return addressBookService.saveAddressBookByParams(addressBook);
	}

	/**
	 * 设置默认地址
	 *
	 * @param addressBook
	 * @return
	 */
	@PutMapping("default")
	public Result<AddressBook> setDefaultAddressBook(@RequestBody AddressBook addressBook) {
		log.info("addressBook:{}", addressBook);
		return addressBookService.setDefaultAddressBook(addressBook);
	}

	/**
	 * 根据id查询地址
	 *
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public Result getAddressBookById(@PathVariable Long id) {
		return addressBookService.getAddressBookById(id);
	}

	/**
	 * 查询默认地址
	 *
	 * @return
	 */
	@GetMapping("default")
	public Result<AddressBook> getDefaultAddressBook() {
		return addressBookService.getDefaultAddressBook();
	}

	/**
	 * 查询指定用户的全部地址
	 *
	 * @param addressBook
	 * @return
	 */
	@GetMapping("/list")
	public Result<List<AddressBook>> getAllAddressBookById(AddressBook addressBook) {
		log.info("addressBook:{}", addressBook);
		return addressBookService.getAllAddressBookById(addressBook);
	}
}
