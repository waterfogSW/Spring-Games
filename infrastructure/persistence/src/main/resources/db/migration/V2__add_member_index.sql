alter table member
    add fulltext index member_name_fulltext_index (name) with parser ngram;
alter table member
    add index member_level_index (level);
