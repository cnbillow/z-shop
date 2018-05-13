package com.iprzd.zshop.service;

import com.iprzd.zshop.controller.response.BaseResponse;
import com.iprzd.zshop.controller.response.CommodityMenuListResponse;
import com.iprzd.zshop.controller.response.StatusCode;
import com.iprzd.zshop.entity.commodity.Menu;
import com.iprzd.zshop.repository.commodity.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CommodityMenuService {

    private MenuRepository menuRepository;

    public CommodityMenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public BaseResponse store(Menu menu) {
        BaseResponse response = new BaseResponse();

        Menu commodityMenu;
        if (menu.getId() > 0) {
            commodityMenu = this.menuRepository.findById(menu.getId()).get();
            commodityMenu.setTitle(menu.getTitle());
            commodityMenu.setParentId(menu.getParentId());
            commodityMenu.setComment(menu.getComment());
        } else {
            commodityMenu = menu;
        }

        menu = this.menuRepository.save(commodityMenu);
        if (menu.getId() > 0) {
            if (menu.getParentId() > 0) {
                Menu parentMenu = this.menuRepository.findById(menu.getParentId()).get();
                parentMenu.setChildren(parentMenu.getChildren() + 1);
                this.menuRepository.save(parentMenu);
            }

            response.setStatus(StatusCode.SUCCESS);
            response.setMessage(StatusCode.getMessage(StatusCode.SUCCESS));
        } else {
            response.setStatus(StatusCode.SAVE_COMMODITY_MENU_FAILED);
            response.setMessage(StatusCode.getMessage(StatusCode.SAVE_COMMODITY_MENU_FAILED));
        }

        return response;
    }

    public BaseResponse delete(long id) {
        BaseResponse response = new BaseResponse();

        if (!this.menuRepository.findById(id).isPresent()) {
            response.setStatus(StatusCode.DELETE_COMMODITY_MENU_FAILED);
            response.setMessage("该栏目已经不存在了，请刷新前端页面。");
            return response;
        }

        Menu menu = this.menuRepository.findById(id).get();
        if (menu.getChildren() > 0) {
            response.setStatus(StatusCode.DELETE_COMMODITY_MENU_FAILED);
            response.setMessage("含有下级栏目，还不能删除。");
            return response;
        }

        this.menuRepository.delete(menu);
        response.setStatus(StatusCode.SUCCESS);
        response.setMessage(StatusCode.getMessage(StatusCode.SUCCESS));
        return response;
    }

    public BaseResponse update(long id, String title, long parentId, String comment) {
        BaseResponse response = new BaseResponse();

        try {
            Menu commodityMenu = this.menuRepository.findById(id).get();
            commodityMenu.setTitle(title);
            commodityMenu.setParentId(parentId);
            commodityMenu.setComment(comment);
            this.menuRepository.save(commodityMenu);

            response.setStatus(StatusCode.SUCCESS);
            response.setMessage(StatusCode.getMessage(StatusCode.SUCCESS));

        } catch (NoSuchElementException e) {
            response.setStatus(StatusCode.CANNOT_FIND_COMMODITY_MENU_EXCEPTION);
            response.setMessage(StatusCode.getMessage(StatusCode.CANNOT_FIND_COMMODITY_MENU_EXCEPTION));
        }

        return response;
    }

    public CommodityMenuListResponse findByParentId(long parentId) {
        CommodityMenuListResponse response = new CommodityMenuListResponse();
        response.setList(this.menuRepository.findByParentId(parentId));
        response.setStatus(StatusCode.SUCCESS);
        response.setMessage(StatusCode.getMessage(StatusCode.SUCCESS));
        return response;
    }
}
