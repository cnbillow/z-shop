-- 商品导航设置
CREATE TABLE `nav_product` (
  `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `product_menu_id` BITINT NOT NULL COMMENT '商品栏目ID',
  `product_menu_title` VARCHAR(255) NOT NULL COMMENT '商品栏目标题' COLLATE 'utf8_unicode_ci',
  `sequence` INT DEFAULT 0 COMMENT '显示顺序'
)
COLLATE='utf8_unicode_ci'
ENGINE=InnoDB
;