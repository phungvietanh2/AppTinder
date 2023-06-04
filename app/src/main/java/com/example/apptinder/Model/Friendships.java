    package com.example.apptinder.Model;

    import static androidx.room.ForeignKey.CASCADE;

    import androidx.room.ColumnInfo;
    import androidx.room.Entity;
    import androidx.room.ForeignKey;
    import androidx.room.Index;
    import androidx.room.PrimaryKey;

    import java.sql.Date;


    @Entity(tableName = "Friendships",
            foreignKeys = {@ForeignKey(entity = User.class,
                    parentColumns = "userId",
                    childColumns = "userId1",
                    onDelete = ForeignKey.CASCADE),
                    @ForeignKey(entity = User.class,
                            parentColumns = "userId",
                            childColumns = "userId2",
                            onDelete = ForeignKey.CASCADE)})
    public class Friendships {
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "friendshipId")
        public int friendshipId;
        @ColumnInfo(name = "userId1")
        public int userId1;
        @ColumnInfo(name = "userId2")
        public int userId2;
        @ColumnInfo(name = "status")
        public String status;

        public Friendships() {
        }

        public int getFriendshipId() {
            return friendshipId;
        }

        public void setFriendshipId(int friendshipId) {
            this.friendshipId = friendshipId;
        }

        public int getUserId1() {
            return userId1;
        }

        public void setUserId1(int userId1) {
            this.userId1 = userId1;
        }

        public int getUserId2() {
            return userId2;
        }

        public void setUserId2(int userId2) {
            this.userId2 = userId2;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
