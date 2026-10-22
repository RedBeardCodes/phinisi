create table if not exists users (
	id integer primary key,
	username text not null,
	password text not null,             -- hashed
	enabled integer not null default 0 check (enabled in (0, 1))
);
