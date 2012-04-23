drop table if exists quiz;
drop table if exists categoryVersion;
drop table if exists category;

create table Category (id bigint not null auto_increment, imageFileName varchar(255) not null, name varchar(255) not null unique, primary key (id));
create table CategoryVersion (id bigint not null auto_increment, categoryVersion integer not null, category_id bigint not null, primary key (id));
create table quiz (id bigint not null auto_increment, answer2 varchar(255) not null, answer3 varchar(255) not null, answer4 varchar(255) not null, categoryVersion integer, correctAnswer varchar(255) not null, question varchar(255) not null, status varchar(10), category_id bigint not null, primary key (id));

alter table CategoryVersion add index FKA1DCB5DA5DF9B7E (category_id), add constraint FKA1DCB5DA5DF9B7E foreign key (category_id) references Category (id);
create index quiz_idx_cateId_ver on quiz (category_id, categoryVersion);
alter table quiz add index FK3522555DF9B7E (category_id), add constraint FK3522555DF9B7E foreign key (category_id) references Category (id);

commit;