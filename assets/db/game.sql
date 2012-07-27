create table PLAYER_PROFILE
(
   PROFILE_ID           INTEGER primary key autoincrement,
   NUMBER               VARCHAR(100),
   NAME                 VARCHAR(100),
   USER_ID              VARCHAR(100) not null,
   PASSWORD             VARCHAR(100) not null,
   CURRENT_SCORE        NUMERIC(10,2) not null,
   INIT_LIMIT           INT,
   LEVEL                INT not null,
   RLS_PATH             VARCHAR(1000) not null,
   ROLE                 VARCHAR(100) not null,
   STATUS               VARCHAR(100),
   CREATE_TIME          DATETIME,
   CREATE_BY            VARCHAR(100),
   UPDATE_TIME          DATETIME,
   UPDATE_BY            VARCHAR(100)
)
