    package com.example.apptinder.Model;

    import static androidx.room.ForeignKey.CASCADE;

    import androidx.room.ColumnInfo;
    import androidx.room.Entity;
    import androidx.room.ForeignKey;
    import androidx.room.Index;
    import androidx.room.PrimaryKey;

    @Entity(tableName = "Conversations",
            foreignKeys = {@ForeignKey(entity = User.class,
                    parentColumns = "userId",
                    childColumns = "userId1",
                    onDelete = ForeignKey.CASCADE),
                    @ForeignKey(entity = User.class,
                            parentColumns = "userId",
                            childColumns = "userId2",
                            onDelete = ForeignKey.CASCADE),
                    @ForeignKey(entity = Messages.class,
                            parentColumns = "messageId",
                            childColumns = "lastMessageId",
                            onDelete = ForeignKey.CASCADE)})
    public class Conversations {
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "conversationId")
        public int conversationId;
        @ColumnInfo(name = "userId1")
        public int userId1;
        @ColumnInfo(name = "userId2")
        public int userId2;
        @ColumnInfo(name = "lastMessageId")
        public Long lastMessageId;

        public Conversations() {
        }

        public int getConversationId() {
            return conversationId;
        }

        public void setConversationId(int conversationId) {
            this.conversationId = conversationId;
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

        public Long getLastMessageId() {
            return lastMessageId;
        }

        public void setLastMessageId(Long lastMessageId) {
            this.lastMessageId = lastMessageId;
        }

        @Override
        public String toString() {
            return "Conversations{" +
                    "conversationId=" + conversationId +
                    ", userId1=" + userId1 +
                    ", userId2=" + userId2 +
                    ", lastMessageId=" + lastMessageId +
                    '}';
        }
    }
