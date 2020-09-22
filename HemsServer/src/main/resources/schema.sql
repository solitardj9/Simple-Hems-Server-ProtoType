CREATE TABLE IF NOT EXISTS SERVICE_SETTING (
	service_name varchar(128),
	access_key varchar(128),
	CONSTRAINT SERVICE_SETTING_PK PRIMARY KEY (service_name)
);

CREATE TABLE IF NOT EXISTS MONITORING_QUEUE_SETTING (
	queue_name varchar(128),
	CONSTRAINT MONITORING_QUEUE_SETTING_PK PRIMARY KEY (queue_name)
);

CREATE TABLE IF NOT EXISTS CONTROL_QUEUE_SETTING (
	queue_name varchar(128),
	CONSTRAINT CONTROL_QUEUE_SETTING_PK PRIMARY KEY (queue_name)
);

CREATE TABLE IF NOT EXISTS KEY_PAIR (
	id IDENTITY NOT NULL PRIMARY KEY,
	public_key LONGTEXT,
	private_key LONGTEXT
);

CREATE TABLE IF NOT EXISTS CERTIFICATE (
	id IDENTITY NOT NULL PRIMARY KEY,
	certificate LONGTEXT
);