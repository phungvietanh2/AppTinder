    package com.example.apptinder.Model;

    import static androidx.room.ForeignKey.CASCADE;

    import androidx.room.ColumnInfo;
    import androidx.room.Entity;
    import androidx.room.ForeignKey;
    import androidx.room.Index;
    import androidx.room.PrimaryKey;

    import java.sql.Date;

        @Entity(tableName = "Messages",
                foreignKeys = {@ForeignKey(entity = User.class,
                        parentColumns = "userId",
                        childColumns = "senderId",
                        onDelete = ForeignKey.CASCADE),
                        @ForeignKey(entity = User.class,
                                parentColumns = "userId",
                                childColumns = "receiverId",
                                onDelete = ForeignKey.CASCADE)})
        public class Messages {
            @PrimaryKey(autoGenerate = true)
            @ColumnInfo(name = "messageId")
            public int messageId;
            @ColumnInfo(name = "senderId")
            public int senderId;
            @ColumnInfo(name = "receiverId")
            public int receiverId;
            @ColumnInfo(name = "content")
            public String content;
            @ColumnInfo(name = "timestamp")
            public String timestamp;

            public Messages() {
            }

        public int getMessageId() {
            return messageId;
        }

        public void setMessageId(int messageId) {
            this.messageId = messageId;
        }

        public int getSenderId() {
            return senderId;
        }

        public void setSenderId(int senderId) {
            this.senderId = senderId;
        }

        public int getReceiverId() {
            return receiverId;
        }

        public void setReceiverId(int receiverId) {
            this.receiverId = receiverId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        @Override
        public String toString() {
            return "Messages{" +
                    "messageId=" + messageId +
                    ", senderId=" + senderId +
                    ", receiverId=" + receiverId +
                    ", content='" + content + '\'' +
                    ", timestamp='" + timestamp + '\'' +
                    '}';
        }
    }
