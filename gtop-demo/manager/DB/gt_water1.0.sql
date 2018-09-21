/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2018/8/1 星期三 16:53:38                        */
/*==============================================================*/


drop table if exists t_wt_brand;

drop table if exists t_wt_carousel;

drop table if exists t_wt_cycle;

drop table if exists t_wt_extensioncode;

drop table if exists t_wt_gift;

drop table if exists t_wt_invoice;

drop table if exists t_wt_order;

drop table if exists t_wt_order_mes;

drop table if exists t_wt_org_setmeal;

drop table if exists t_wt_partner;

drop table if exists t_wt_product;

drop table if exists t_wt_profit;

drop table if exists t_wt_profit_city;

drop table if exists t_wt_profit_partner;

drop table if exists t_wt_profit_system;

drop table if exists t_wt_profit_waterstore;

drop table if exists t_wt_send;

drop table if exists t_wt_send_mes;

drop table if exists t_wt_sku;

drop table if exists t_wt_ticket_log;

drop table if exists t_wt_urge;

drop table if exists t_wt_user;

drop table if exists t_wt_user_address;

drop table if exists t_wt_user_detailed;

drop table if exists t_wt_user_extensioncode;

drop table if exists t_wt_user_ticket;

drop table if exists t_wt_waterstop_setmeal;

drop table if exists t_wt_waterstore;

drop table if exists t_wt_waterstore_sku;

/*==============================================================*/
/* Table: t_wt_brand                                            */
/*==============================================================*/
create table t_wt_brand
(
   id                   bigint not null comment '主键',
   name                 varchar(256) comment '品牌名称',
   messages             varchar(256) comment '品牌描述',
   create_time          bigint comment '创建时间',
   create_id            bigint comment '创建人',
   del_state            int comment '删除状态',
   primary key (id)
);

alter table t_wt_brand comment '品牌表';

/*==============================================================*/
/* Table: t_wt_carousel                                         */
/*==============================================================*/
create table t_wt_carousel
(
   id                   bigint not null comment '主键',
   waterstore_id        bigint comment '水站ID',
   access_url           varchar(256) comment '访问地址',
   carousel_pic         varchar(128) comment '轮播图片',
   create_id            bigint comment '创建者',
   create_time          bigint comment '创建时间',
   update_by            bigint comment '修改者',
   update_time          bigint comment '修改时间',
   del_state            int default 0 comment '删除标记',
   version              bigint(20) default 1 comment '数据版本',
   primary key (id)
);

alter table t_wt_carousel comment '水站轮播图';

/*==============================================================*/
/* Table: t_wt_cycle                                            */
/*==============================================================*/
create table t_wt_cycle
(
   cycle_id             bigint not null comment '周期ID',
   user_name            varchar(256) comment '用户名称',
   user_id              bigint comment '用户ID',
   send_id              bigint comment '派送单ID',
   order_time           bigint comment '下单时间',
   send_time_start      bigint comment '开始派送时间',
   send_time_end        bigint comment '最后一次时间',
   company              varchar(256) comment '公司名称',
   create_time          bigint comment '创建时间',
   create_id            bigint comment '创建人',
   del_state            int comment '删除状态 1正常、0删除',
   primary key (cycle_id)
);

alter table t_wt_cycle comment '周期订单';

/*==============================================================*/
/* Table: t_wt_extensioncode                                    */
/*==============================================================*/
create table t_wt_extensioncode
(
   id                   bigint not null comment '推广码ID',
   messages             varchar(1000) comment '推广码内容',
   partner_id           bigint comment '绑定合伙人ID',
   register_time        bigint comment '绑定时间',
   create_time          bigint comment '创建时间',
   create_id            bigint comment '创建人',
   del_state            int comment '删除状态 1正常、0删除',
   primary key (id)
);

alter table t_wt_extensioncode comment '推广码信息表';

/*==============================================================*/
/* Table: t_wt_gift                                             */
/*==============================================================*/
create table t_wt_gift
(
   id                   bigint not null comment '赠送ID',
   gift_name            bigint comment '赠品名称',
   product_id           bigint comment '赠品ID',
   sku_code             varchar(256) comment '赠品SKU',
   pick                 bigint comment '赠品当前价格',
   mes                  varchar(512) comment '赠品描述',
   order_code           varchar(256) comment '订单ID',
   user_id              bigint comment '客户',
   sigen_name           varchar(256) comment '签收人',
   create_time          bigint comment '创建时间',
   create_id            bigint comment '创建人',
   del_state            int comment '删除状态 1正常、0删除',
   primary key (id)
);

alter table t_wt_gift comment '赠品记录';

/*==============================================================*/
/* Table: t_wt_invoice                                          */
/*==============================================================*/
create table t_wt_invoice
(
   id                   bigint not null comment '发票ID',
   order_code           varchar(256) comment '订单ID',
   user_id              bigint comment '用户（客户）ID',
   money                bigint comment '发票金额',
   invoice_date         bigint comment '开票日期',
   waterstore_id        bigint comment '水站ID',
   state                int comment '发票状态【1未送达、2已送达】',
   sign_date            bigint comment '签收日期',
   sign_name            varchar(256) comment '签收人',
   title                varchar(512) comment '发票台头',
   duty_no              varchar(256) comment '税号',
   send_name            varchar(256) comment '派送人',
   create_time          bigint comment '创建时间',
   create_id            bigint comment '创建人',
   del_state            int comment '删除状态',
   primary key (id)
);

alter table t_wt_invoice comment '发票';

/*==============================================================*/
/* Table: t_wt_order                                            */
/*==============================================================*/
create table t_wt_order
(
   id                   bigint comment '订单ID',
   order_no             varchar(256) comment '订单编号',
   waterstore_id        bigint comment '水站ID',
   user_id              bigint comment '用户（客户）ID',
   province_id          varchar(50) comment '省ID',
   province             varchar(256) comment '省',
   city_id              varchar(50) comment '市ID',
   city                 varchar(256) comment '市',
   area_Id              varchar(50) comment '区ID',
   area                 varchar(256) comment '区',
   address              varchar(500) comment '详细地址',
   money                bigint comment '订单金额',
   remarks              varchar(512) comment '买家留言',
   is_invoice           int comment '是否发票',
   is_setmeal           int comment '是否有套餐',
   is_ticket            int comment '是否水票抵扣',
   ticket_money         bigint comment '水票抵扣金额',
   payment_money        bigint comment '付款金额',
   payment_type         int comment '付款方式【1微信】',
   order_state          int comment '订单状态【1订单确认、2代付款、3完成】',
   order_time           bigint comment '下单时间',
   payment_time         bigint comment '付款时间',
   extensioncode_id     bigint comment '推广码ID',
   create_time          bigint comment '创建时间',
   create_id            bigint comment '创建人',
   del_state            int comment '删除状态 1正常、0删除'
);

alter table t_wt_order comment '订单';

/*==============================================================*/
/* Table: t_wt_order_mes                                        */
/*==============================================================*/
create table t_wt_order_mes
(
   id                   bigint not null comment '详情ID',
   order_no             varchar(256) comment '订单编号',
   order_mes_no         varchar(256) comment '订单详情编号',
   sku_code             varchar(256) comment 'SKU编号',
   sku_name             varchar(256) comment 'SKU名称',
   type                 int comment '1水票|| 2桶水',
   price                bigint comment '售价',
   num                  int comment '数量',
   total_price          bigint comment '总价',
   sequence             varchar(1024) comment '商品序列',
   create_time          bigint comment '创建时间',
   create_id            bigint comment '创建人',
   del_state            int comment '删除状态 1正常、0删除',
   primary key (id)
);

alter table t_wt_order_mes comment '订单详情';

/*==============================================================*/
/* Table: t_wt_org_setmeal                                      */
/*==============================================================*/
create table t_wt_org_setmeal
(
   id                   bigint not null comment '主键',
   branches_id          bigint comment '机构(城市)ID',
   brand_id             bigint comment '品牌',
   sku_code             varchar(256) comment '商品SKU code',
   setmeal_code         varchar(128) comment '套餐编号',
   series_sku_code      varchar(128) comment '套系编号（sku）',
   name                 varchar(128) comment '套餐名称',
   series_name          varchar(128) comment '套系名称',
   setmeal_img          varchar(512) comment '套餐宣传图片',
   shopcart_img         varchar(512) comment '购物车图片',
   goods_profile        longtext comment '商品图文',
   num                  int comment '数量',
   introduce            varchar(512) comment '套餐介绍',
   price                bigint comment '套餐价格',
   is_gift              int comment '是否有赠品【1有，0无】',
   remark               varchar(512) comment '备注',
   onshelf_state        int comment '上架状态【1上架、0下架】',
   onshelf_time         bigint comment '上架时间',
   create_time          bigint comment '创建时间',
   create_id            bigint comment '创建人',
   del_state            int comment '删除状态 1正常、0删除',
   primary key (id)
);

alter table t_wt_org_setmeal comment '机构套餐';

/*==============================================================*/
/* Table: t_wt_partner                                          */
/*==============================================================*/
create table t_wt_partner
(
   id                   bigint not null comment 'partner_id',
   user_id              bigint comment '用户ID',
   open_id              bigint comment '开放平台用户ID',
   phone                varchar(128) comment '电话号码',
   user_name            varchar(256) comment '用户名',
   idcard               varchar(128) comment '身份证号',
   card_front           varchar(512) comment '身份证正面照',
   card_back            varchar(512) comment '身份证反面照',
   nickname             varchar(256) comment '昵称',
   sex                  int comment '性别(1男、0女)',
   country              varchar(256) comment '国家',
   province_id          varchar(50) comment '省ID',
   province             varchar(256) comment '省',
   city_id              varchar(50) comment '市ID',
   city                 varchar(256) comment '市',
   area_Id              varchar(50) comment '区ID',
   area                 varchar(256) comment '区',
   address              varchar(500) comment '详细地址',
   language             varchar(128) comment '语言',
   register_date        bigint comment '注册时间',
   income_money         bigint comment '累计收入',
   total_money          bigint comment '总金额',
   extract_money        bigint comment '提现金额',
   customer_num         bigint comment '客户数量',
   or_code_id           varchar(1) comment '二维码',
   portrait             varchar(512) comment '用户头像',
   update_time          bigint comment '更新时间',
   remark               varchar(512) comment '备注',
   create_time          bigint comment '创建时间',
   create_id            bigint comment '创建人',
   del_state            int comment '删除状态 1正常、0删除',
   primary key (id)
);

alter table t_wt_partner comment '合伙人表';

/*==============================================================*/
/* Table: t_wt_product                                          */
/*==============================================================*/
create table t_wt_product
(
   id                   bigint not null comment '商品id',
   branches_id          bigint comment '机构ID',
   gtop_goods_id        bigint comment '商品（公共）id',
   spu_code             varchar(256) comment 'SKU编号',
   brand_id             bigint comment '品牌ID',
   type_code            varchar(256) comment '类别编号',
   goods_name           varchar(128) comment '商品名称',
   goods_photos         varchar(512) comment '商品图片（多个）',
   goods_pic            varchar(128) comment '商品购物车图片',
   goods_source         int default 1 comment '商品来源（1：平台商品，2：店铺自定义商品）',
   goods_profile        longtext comment '商品图文',
   create_id            bigint comment '创建者',
   create_time          bigint comment '创建时间',
   update_id            bigint comment '修改者',
   update_time          bigint comment '修改时间',
   del_state            int default 2 comment '删除状态【1正常，0删除】',
   version              bigint(20) default 1 comment '数据版本',
   primary key (id)
);

alter table t_wt_product comment '机构_商品_价格表';

/*==============================================================*/
/* Table: t_wt_profit                                           */
/*==============================================================*/
create table t_wt_profit
(
   id                   bigint,
   send_id              bigint comment '派送编号',
   user_id              bigint comment '用户ID',
   waterstore_id        bigint comment '水站ID',
   partner_id           bigint comment '合伙人ID',
   send_num             int comment '派送总量',
   profit_money         bigint comment '派单金额',
   payment_time         bigint comment '付款时间',
   send_time            bigint comment '派送时间',
   create_time          bigint comment '创建时间'
);

alter table t_wt_profit comment '分佣表';

/*==============================================================*/
/* Table: t_wt_profit_city                                      */
/*==============================================================*/
create table t_wt_profit_city
(
   id                   bigint not null,
   branches_id          bigint comment '机构（城市）ID',
   user_id              bigint comment '城市经理ID',
   profit_id            bigint comment '分佣ID',
   order_code           varchar(256) comment '订单明细编号',
   order_num            int comment '订单数量',
   unit_price           bigint comment '订单金额',
   sku_code             varchar(128) comment 'SKU编号',
   profit_money         bigint comment '分佣金额',
   order_time           bigint comment '下单时间',
   proportion           int comment '分佣比例',
   create_time          bigint comment '创建时间',
   create_id            bigint comment '创建人',
   del_status           int comment '删除状态',
   primary key (id)
);

alter table t_wt_profit_city comment '平台分佣表';

/*==============================================================*/
/* Table: t_wt_profit_partner                                   */
/*==============================================================*/
create table t_wt_profit_partner
(
   id                   bigint not null,
   profit_id            bigint comment '分佣ID',
   order_code           varchar(256) comment '订单明细编号',
   order_num            int comment '订单数量',
   unit_price           bigint comment '商品单价',
   sku_code             varchar(128) comment 'SKU编号',
   profit_money         bigint comment '分佣金额',
   order_time           bigint comment '下单时间',
   proportion           int comment '分佣比例',
   branches_id          bigint comment '机构ID',
   partner_id           bigint comment '合伙人ID',
   create_time          bigint comment '创建时间',
   create_id            bigint comment '创建人',
   del_status           int comment '删除状态',
   primary key (id)
);

alter table t_wt_profit_partner comment '分佣(合伙人)表';

/*==============================================================*/
/* Table: t_wt_profit_system                                    */
/*==============================================================*/
create table t_wt_profit_system
(
   id                   bigint not null,
   profit_id            bigint comment '分佣ID',
   order_code           varchar(256) comment '订单明细编号',
   order_num            int comment '订单数量',
   unit_price           bigint comment '商品单价',
   sku_code             varchar(128) comment 'SKU编号',
   profit_money         bigint comment '分佣金额',
   order_time           bigint comment '下单时间',
   proportion           int comment '分佣比例',
   create_time          bigint comment '创建时间',
   create_id            bigint comment '创建人',
   del_status           int comment '删除状态',
   primary key (id)
);

alter table t_wt_profit_system comment '平台分佣表';

/*==============================================================*/
/* Table: t_wt_profit_waterstore                                */
/*==============================================================*/
create table t_wt_profit_waterstore
(
   id                   bigint not null comment '主键',
   profit_id            bigint comment '分佣ID',
   order_code           varchar(256) comment '订单明细编号',
   order_num            int comment '订单数量',
   unit_price           bigint comment '商品单价',
   waterstore_id        bigint comment '水站ID',
   branches_id          bigint comment '开放平台水站ID',
   water_name           varchar(256) comment '水站名称',
   waterstore_no        varchar(256) comment '水站编号',
   sku_code             varchar(128) comment 'SKU编号',
   proportion           int comment '分佣比例',
   profit_money         bigint comment '分佣金额',
   create_time          bigint comment '创建时间',
   create_id            bigint comment '创建人',
   del_status           int comment '删除状态',
   primary key (id)
);

alter table t_wt_profit_waterstore comment '分佣(水站)表';

/*==============================================================*/
/* Table: t_wt_send                                             */
/*==============================================================*/
create table t_wt_send
(
   id                   bigint not null comment '派送ID',
   send_no              varchar(128) comment '派送单编号',
   waterstore_id        bigint comment '水站ID',
   province_id          varchar(50) comment '省ID',
   province             varchar(256) comment '省',
   city_id              varchar(50) comment '市ID',
   city                 varchar(256) comment '市',
   area_Id              varchar(50) comment '区ID',
   area                 varchar(256) comment '区',
   address              varchar(500) comment '详细地址',
   user_id              bigint comment '用户（客户）ID',
   contacts             varchar(256) comment '联系人',
   phone                varchar(128) comment '联系电话',
   appointment_time     bigint comment '预约时间',
   send_time            bigint comment '实际派送时间',
   send_user            varchar(256) comment '派送人',
   sigen_user           varchar(256) comment '签收人',
   status               int comment '状态【0未接单、1确认接单 （待收货）、5送达（完成）、6改派、-1取消】',
   change_send_no       varchar(128) comment '改派后单号',
   remarks              varchar(512) comment '备注',
   create_time          bigint comment '创建时间',
   create_id            bigint comment '创建人',
   del_status           int comment '删除状态',
   primary key (id)
);

alter table t_wt_send comment '派送订单';

/*==============================================================*/
/* Table: t_wt_send_mes                                         */
/*==============================================================*/
create table t_wt_send_mes
(
   id                   bigint comment '详情ID',
   send_code            varchar(256) comment '派送单编号',
   send_mes_code        varchar(256) comment '派送单详情编号',
   sku_code             varchar(256) comment 'SKU编号',
   sku_name             varchar(256) comment 'SKU名称',
   num                  int comment '数量',
   type                 int comment '1水票|| 2水 || 混合支付',
   sequence             varchar(1024) comment '商品序列',
   order_mes_code       varchar(128) binary comment '订单详情CODE（如果type = 1票，此字段为空；如果type=2水，填写关联的订单号；type=3 混合支付（现金&水票），此字段为现金支付对应的订单明细code)',
   create_time          bigint comment '创建时间',
   create_id            bigint comment '创建人',
   del_state            int comment '删除状态 1正常、0删除'
);

alter table t_wt_send_mes comment '派送详情';

/*==============================================================*/
/* Table: t_wt_sku                                              */
/*==============================================================*/
create table t_wt_sku
(
   id                   bigint not null comment '主键',
   branches_id          bigint comment '机构(城市)ID',
   p_id                 bigint comment '商品ID',
   goods_code           varchar(128) comment '商品编号（SPU编号）',
   brand_id             bigint comment '品牌ID',
   sku_name             varchar(256) comment 'SKU名称',
   sku_code             varchar(256) comment 'SKU编号',
   goods_bar            varchar(32) comment '商品条形码',
   type_code            varchar(256) comment '类别编号，多级 ，‘000005000005’ 6位代表一级编号',
   goods_spec           varchar(64) comment '商品规格',
   goods_weight         decimal(10,4) comment '商品的重量',
   goods_util           int comment '单位值（1:g, 2:kg, 3：ml， 4:L)',
   goods_size           varchar(256) comment '尺寸',
   goods_color          varchar(256) comment '颜色',
   status               int default 1 comment '状态[1销售中（上架），2下架,3审核中，4审核不通过]',
   cost_price           bigint default 0.00 comment '成本价格，（平台商品取自平台，自定义商品0）',
   price                bigint default 0.00 comment '市场价',
   on_sales             int comment '是否为特价【1是特价、0不是】',
   sell_price           bigint default 0.00 comment '水站销售价格',
   shelf_on_time        bigint comment '上架时间',
   shell_off_reason     varchar(128) comment '下架原因',
   create_id            bigint comment '创建者',
   create_time          bigint comment '创建时间',
   update_id            bigint comment '修改者',
   update_time          bigint comment '修改时间',
   del_state            int default 2 comment '删除状态【1正常，0删除】',
   version              bigint default 1 comment '数据版本',
   primary key (id)
);

alter table t_wt_sku comment '商品sku表';

/*==============================================================*/
/* Table: t_wt_ticket_log                                       */
/*==============================================================*/
create table t_wt_ticket_log
(
   id                   bigint not null comment '记录ID',
   ticket_id            bigint comment '水票ID',
   sku_name             varchar(256) comment 'SKU名称',
   sku_code             varchar(256) comment 'SKU编号',
   num                  int comment '数量',
   operation            int comment '增/减 【1增、-1减】',
   type                 int comment '订单/派送单',
   user_id              bigint comment '用户(客户)ID',
   user_name            varchar(256) comment '用户（客户）名称',
   address              varchar(512) comment '地址',
   send_mes_code        varchar(256) comment '派送单明细code',
   order_mes_code       varchar(256) comment '订单明细code',
   log_time             bigint comment '消费时间',
   create_time          bigint comment '创建时间',
   create_id            bigint comment '创建人',
   del_state            int comment '删除状态 1正常、0删除',
   primary key (id)
);

alter table t_wt_ticket_log comment '水票消费记录表';

/*==============================================================*/
/* Table: t_wt_urge                                             */
/*==============================================================*/
create table t_wt_urge
(
   id                   bigint not null comment '催单ID',
   user_id              bigint comment '用户（客户）ID',
   user_name            varchar(256) comment '用户（客户）名称',
   send_id              bigint comment '派单ID',
   remarks              varchar(512) comment '备注',
   create_time          bigint comment '创建时间',
   create_id            bigint comment '创建人',
   del_state            int comment '删除状态',
   primary key (id)
);

alter table t_wt_urge comment '催单表';

/*==============================================================*/
/* Table: t_wt_user                                             */
/*==============================================================*/
create table t_wt_user
(
   id                   bigint not null comment '系统唯一id',
   create_time          timestamp default CURRENT_TIMESTAMP comment '生成时间',
   login_time           timestamp default CURRENT_TIMESTAMP comment '登录时间',
   login_source         int(11) comment '登录来源 1微信 2QQ',
   open_code            varchar(256) comment '微信编号',
   partner_id           bigint comment '所属合伙人',
   phone                varchar(32) comment '电话号码',
   nickname             varchar(64) comment '昵称',
   username             varchar(64) comment '用户名',
   password             varchar(64) comment '密码',
   icon                 varchar(256) comment '头像url',
   voice_prompt_switch  char(1) not null default '0' comment '语音提示开关 0:否  1:是',
   create_time2         bigint comment '创建时间',
   create_id            bigint comment '创建人',
   del_status           int comment '删除状态',
   primary key (id)
);

alter table t_wt_user comment '用户信息表';

/*==============================================================*/
/* Table: t_wt_user_address                                     */
/*==============================================================*/
create table t_wt_user_address
(
   id                   bigint not null comment 'ID',
   user_id              bigint comment 'user_id',
   title                varchar(512) comment 'title',
   address              varchar(512) comment '地址',
   is_default           int comment '是否默认【1默认、0否】',
   province_id          varchar(50) comment '省ID',
   province             varchar(256) comment '省',
   city_id              varchar(50) comment '市ID',
   city                 varchar(256) comment '市',
   area_Id              varchar(50) comment '区ID',
   area                 varchar(256) comment '区',
   remark               varchar(512) comment '备注',
   create_time          bigint comment '创建时间',
   create_id            bigint comment '创建人',
   del_state            int comment '删除状态 1正常、0删除',
   primary key (id)
);

alter table t_wt_user_address comment '用户地址表';

/*==============================================================*/
/* Table: t_wt_user_detailed                                    */
/*==============================================================*/
create table t_wt_user_detailed
(
   id                   bigint not null comment 'ID',
   user_id              bigint comment 'user_id',
   title                varchar(256) comment 'title',
   duty_no              varchar(128) comment '税号',
   is_default           int comment '是否默认【1是、0否】',
   remarks              varchar(500) comment '备注',
   create_time          bigint comment '创建时间',
   create_id            bigint comment '创建人',
   del_state            int comment '删除状态 1正常、0删除',
   primary key (id)
);

alter table t_wt_user_detailed comment '用户发票信息表';

/*==============================================================*/
/* Table: t_wt_user_extensioncode                               */
/*==============================================================*/
create table t_wt_user_extensioncode
(
   id                   bigint not null comment '记录ID',
   code_id              bigint comment '二维码ID',
   user_id              bigint comment '用户ID',
   user_name            varchar(256) comment '用户名称',
   regedit_time         bigint comment '扫码时间',
   create_time          bigint comment '创建时间',
   create_id            bigint comment '创建人',
   del_state            int comment '删除状态 1正常、0删除',
   primary key (id)
);

alter table t_wt_user_extensioncode comment '用户扫码记录表';

/*==============================================================*/
/* Table: t_wt_user_ticket                                      */
/*==============================================================*/
create table t_wt_user_ticket
(
   id                   bigint not null comment '用户水票ID',
   user_id              bigint comment '用户ID',
   num                  int comment '水票数量',
   surplus_num          int comment '水票余量',
   ticket_price         bigint comment '水票单价',
   sku_code             varchar(128) comment '商品ID',
   order_code           varchar(128) comment '订单明细编号',
   order_time           bigint comment '订单时间',
   update_time          bigint comment '修改时间',
   create_time          bigint comment '创建时间',
   create_id            bigint comment '创建人',
   del_state            int comment '删除状态 1正常、0删除',
   primary key (id)
);

alter table t_wt_user_ticket comment '用户水票表';

/*==============================================================*/
/* Table: t_wt_waterstop_setmeal                                */
/*==============================================================*/
create table t_wt_waterstop_setmeal
(
   id                   bigint not null comment '主键',
   setmeal_id           bigint comment '机构套餐ID',
   branches_id          bigint comment '机构(水站)ID',
   waterstore_id        bigint comment '水站ID',
   onshelf_state        int comment '上架状态【1上架、0下架】',
   onshelf_time         bigint comment '上架时间',
   create_time          bigint comment '创建时间',
   create_id            bigint comment '创建人',
   del_state            int comment '删除状态 1正常、0删除',
   primary key (id)
);

alter table t_wt_waterstop_setmeal comment '水站套餐';

/*==============================================================*/
/* Table: t_wt_waterstore                                       */
/*==============================================================*/
create table t_wt_waterstore
(
   id                   bigint not null comment 'ID',
   branches_id          bigint comment '开放平台机构编号',
   water_name           varchar(256) comment '水站名称',
   show_name            varchar(256) comment '水站显示名称',
   org_id               bigint comment '组织机构ID',
   waterstore_no        varchar(256) comment '水站编号',
   tel                  varchar(128) comment '站点电话',
   create_time          bigint comment '创建时间',
   create_id            bigint comment '创建人',
   del_status           int comment '删除状态',
   org_code             varchar(50) comment '组织机构编号',
   parent_id            bigint(20) comment '父级ID',
   org_state            int(11) comment '状态 0待运营、1运营中、2已关闭',
   audit_request_time   bigint(20) comment '提交审核时间',
   audit_time           bigint(20) comment '审核时间',
   auditor_id           bigint(20) comment '审核人',
   audit_state          int(2) comment '审核状态 ：-1审核中、0审核失败、1审核成功',
   approve_code         varchar(255) comment '审批编号',
   create_id2           bigint(20) comment '创建人',
   update_id            bigint(20) comment '修改人',
   update_time          bigint(20) comment '修改时间',
   is_delete            int(1) default 1 comment '是否删除0删除，1正常',
   self_life_state      int(2) comment '是否可以自提，1是自提，0与null非自提',
   self_life_address    varchar(254) comment '自提地址',
   self_life_phone      varchar(254) comment '自提手机号,多个手机号用","号分割',
   self_life_time       varchar(100) comment '自提时间格式hh:mm-hh:mm',
   service_range        varchar(9998) comment '服务范围(派送范围的经纬度)',
   service_range_mark   varchar(2000) comment '服务范围备注信息',
   bank_account_name    varchar(50) comment '银行账号姓名（户名）',
   open_bank            varchar(50) comment '开户行',
   bank_account         varchar(50) comment '银行账号',
   bank_name            varchar(50) comment '银行名称',
   service_station_location varchar(255) comment '服务站位置',
   service_station_area varchar(255) comment '服务站面积',
   service_station_type int(3) comment '服务站类型1-居民楼，2-办公楼，3-社区，4-校园',
   branches_type        int(1) comment '门店类型：1直营，2加盟商',
   branches_img         varchar(100) comment '门店照片',
   branches_sort        int(4) default 99 comment '门店排序',
   is_show              int(1) default 1 comment '是否展示1展示，0不展示',
   link_phone           varchar(50) comment '联系电话',
   link_name            varchar(50) comment '联系人',
   send_time            time comment '配送时间',
   operate_end_time     time comment '每天运营结束时间',
   operate_start_time   time comment '每天运营开始时间',
   operate_week         varchar(32) comment '运营时间周 【0,0,0,0,0,0,0】7位字符串，从周一至周日每一位代表一天，1代表上班，0代表休息，以英文逗号分隔',
   mail                 varchar(50) comment 'Mail',
   primary key (id)
);

alter table t_wt_waterstore comment '水站表';

/*==============================================================*/
/* Table: t_wt_waterstore_sku                                   */
/*==============================================================*/
create table t_wt_waterstore_sku
(
   id                   bigint not null comment '主键',
   sku_code             varchar(256) comment 'SKU编号',
   branches_id          bigint comment '机构(水站)ID',
   waterstore_id        bigint comment '水站ID',
   status               int default 1 comment '状态[1销售中（上架），2下架]',
   shelf_on_time        bigint comment '上架时间',
   shell_off_reason     varchar(128) comment '下架原因',
   create_id            bigint comment '创建者',
   create_time          bigint comment '创建时间',
   update_id            bigint comment '修改者',
   update_time          bigint comment '修改时间',
   del_state            int default 2 comment '删除状态【1正常，0删除】',
   version              bigint default 1 comment '数据版本',
   primary key (id)
);

alter table t_wt_waterstore_sku comment '水站商品sku表';
