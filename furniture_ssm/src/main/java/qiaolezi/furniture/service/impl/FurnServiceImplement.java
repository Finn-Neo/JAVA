package qiaolezi.furniture.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qiaolezi.furniture.bean.Furn;
import qiaolezi.furniture.dao.FurnMapper;
import qiaolezi.furniture.service.FurnService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: furniture_ssm
 * @author: Qiaolezi
 * @create: 2024-05-10 17:13
 * @description:
 **/
@Service
public class FurnServiceImplement implements FurnService {

	//注入FurnMapper接口对象（代理对象）
	@Resource
	private FurnMapper furnMapper;

	@Override
	public void save(Furn furn) {
		//因为furniture表的字段id是自增的，因此使用insertSelective
		furnMapper.insertSelective(furn);
	}

	@Override
	public List<Furn> findAll() {
		return furnMapper.selectByExample(null);
	}

	@Override
	public Furn findById(Integer id) {
		return furnMapper.selectByPrimaryKey(id);
	}

	@Override
	public void delete(Integer id) {
		furnMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void update(Furn furn) {
		furnMapper.updateByPrimaryKeySelective(furn);
	}
}
