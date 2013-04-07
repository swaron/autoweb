CREATE TABLE t_account
(
  t_account_id integer auto_increment,
  account_area varchar(6) DEFAULT NULL,
  account_post_area varchar(128) DEFAULT NULL,
  account_applay_area varchar(128) DEFAULT NULL,
  account_type integer,
  account_status smallint,
  account_bank_card_no varchar(19) DEFAULT NULL,
  account_no varchar(24) DEFAULT NULL,
  account_name varchar(32) DEFAULT NULL,
  account_reg_ip varchar(16) DEFAULT NULL,
  account_login_ip varchar(16) DEFAULT NULL,
  password varchar(8) DEFAULT NULL,
  log_fail_count integer,
  phoneno varchar(11) DEFAULT NULL,
  mail varchar(64) DEFAULT NULL,
  mac varchar(17) DEFAULT NULL,
  explore_info varchar(50) DEFAULT NULL,
  account_created_time timestamp,
  account_updated_time timestamp,
  CONSTRAINT t_account_pkey PRIMARY KEY (t_account_id)
);

CREATE TABLE t_trade
(
  t_trade_id integer auto_increment,
  trade_time timestamp,
  trade_numbers integer,
  turn_over_single real,
  trade_amount real,
  trade_wave real,
  trade_area varchar(128),
  trade_type integer,
  trade_kind integer,
  trade_status_code integer,
  trade_direct smallint,
  trade_card_no varchar(19),
  trade_bank_card_no varchar(19),
  payer_account_no varchar(24),
  payer_account_id bigint,
  payee_account_no varchar(24),
  payee_account_id bigint,
  trade_ip varchar(15),
  trade_mac varchar(17),
  explore_info varchar(50),
  trade_created_time timestamp,
  trade_updated_time timestamp,
  CONSTRAINT t_trade_pkey PRIMARY KEY (t_trade_id)
);

CREATE TABLE t_trade_current
(
  t_trade_id integer auto_increment,
  trade_time timestamp,
  trade_numbers integer,
  turn_over_single real,
  trade_amount real,
  trade_wave real,
  trade_area varchar(128),
  trade_type integer,
  trade_kind integer,
  trade_status_code integer,
  trade_direct smallint,
  trade_card_no varchar(19),
  trade_bank_card_no varchar(19),
  payer_account_no varchar(24),
  payer_account_id bigint,
  payee_account_no varchar(24),
  payee_account_id bigint,
  trade_ip varchar(15),
  trade_mac varchar(17),
  explore_info varchar(50),
  trade_created_time timestamp,
  trade_updated_time timestamp,
  CONSTRAINT t_trade_pkey PRIMARY KEY (t_trade_id)
);

