package top.naccl.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.naccl.entity.CityVisitor;
import top.naccl.mapper.CityVisitorMapper;
import top.naccl.service.CityVisitorService;

/**
 * @Description: 城市访客数量统计业务层实现
 * @Author: Ahui
 * @Date: 2021-02-26
 */
@Service
public class CityVisitorServiceImpl implements CityVisitorService {
	@Autowired
	CityVisitorMapper cityVisitorMapper;

	@Override
	public void saveCityVisitor(CityVisitor cityVisitor) {
		cityVisitorMapper.saveCityVisitor(cityVisitor);
	}
}
