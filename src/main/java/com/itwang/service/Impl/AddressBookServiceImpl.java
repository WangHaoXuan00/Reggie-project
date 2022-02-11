package com.itwang.service.Impl;/**
 * @author Whx
 * @company XXX
 * @create 2022-02-03 18:30
 */

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itwang.entity.AddressBook;
import com.itwang.mapper.AddressBookMapper;
import com.itwang.service.AddressBookService;
import org.springframework.stereotype.Service;

/**
 * @packageName: com.itwang.service.Impl
 * @ClassName: AddressBookServiceImpl
 * @Description:
 * @author Whx
 * @data 2022/2/3 TIME:18:30
 * */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {

}

