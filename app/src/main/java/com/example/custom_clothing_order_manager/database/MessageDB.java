package com.example.custom_clothing_order_manager.database;

import static android.icu.text.ListFormatter.Type.AND;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.custom_clothing_order_manager.models.Customer;
import com.example.custom_clothing_order_manager.models.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "messages.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "messages";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_SENDER_ID = "sender_id";
    public static final String COLUMN_RECEIVER_ID = "receiver_id";
    public static final String COLUMN_MESSAGE = "message";
    public static final String COLUMN_TIMESTAMP = "timestamp";
    public MessageDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_SENDER_ID + " INTEGER, " +
                        COLUMN_RECEIVER_ID + " INTEGER, " +
                        COLUMN_MESSAGE + " TEXT, " +
                        COLUMN_TIMESTAMP + " TEXT);";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertMessage(SQLiteDatabase db, Message message) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_SENDER_ID, message.getSenderId());
        values.put(COLUMN_RECEIVER_ID, message.getReceiverId());
        values.put(COLUMN_MESSAGE, message.getMessage());
        values.put(COLUMN_TIMESTAMP, message.getTimestamp());
        db.insert(TABLE_NAME, null, values);
    }

    public List<Message> getMessagesBySenderAndReceiver(int senderId, int receiverId) {
        List<Message> messages = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_SENDER_ID + " = ? AND " + COLUMN_RECEIVER_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(senderId), String.valueOf(receiverId)});

        if (cursor != null && cursor.moveToFirst()) {
            try {
                int idIndex = cursor.getColumnIndexOrThrow(COLUMN_ID);
                int senderIdIndex = cursor.getColumnIndexOrThrow(COLUMN_SENDER_ID);
                int receiverIdIndex = cursor.getColumnIndexOrThrow(COLUMN_RECEIVER_ID);
                int messageIndex = cursor.getColumnIndexOrThrow(COLUMN_MESSAGE);
                int timestampIndex = cursor.getColumnIndexOrThrow(COLUMN_TIMESTAMP);

                do {
                    int id = cursor.getInt(idIndex);
                    int sender = cursor.getInt(senderIdIndex);
                    int receiver = cursor.getInt(receiverIdIndex);
                    String messageText = cursor.getString(messageIndex);
                    String timestamp = cursor.getString(timestampIndex);

                    Message message = new Message(id, sender, receiver, messageText, timestamp);
                    messages.add(message);

                } while (cursor.moveToNext());
            } catch (IllegalArgumentException e) {
                Log.e("MessageDB", "Column not found", e);
            } finally {
                cursor.close();
            }
        }
        return messages;
    }

    public List<Integer> getAllSenders() {
        List<Integer> senders = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT DISTINCT " + COLUMN_SENDER_ID + " FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            try {
                int senderIdIndex = cursor.getColumnIndex(COLUMN_SENDER_ID);
                if (senderIdIndex != -1) {
                    do {
                        int senderId = cursor.getInt(senderIdIndex);
                        senders.add(senderId);
                    } while (cursor.moveToNext());
                } else {
                    Log.e("MessageDB", "Column " + COLUMN_SENDER_ID + " not found");
                }
            } catch (Exception e) {
                Log.e("MessageDB", "Error fetching sender IDs", e);
            } finally {
                cursor.close();
            }
        }
        return senders;
    }

    public List<Message> getLatestMessagesForChats(int senderId) {
        List<Message> chatList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID +
                " IN (SELECT MAX(" + COLUMN_ID + ") FROM " + TABLE_NAME +
                " WHERE " + COLUMN_SENDER_ID + " = ? OR " + COLUMN_RECEIVER_ID + " = ? " +
                " GROUP BY CASE WHEN " + COLUMN_SENDER_ID + " = ? THEN " + COLUMN_RECEIVER_ID +
                " ELSE " + COLUMN_SENDER_ID + " END) ORDER BY " + COLUMN_TIMESTAMP + " DESC";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(senderId), String.valueOf(senderId), String.valueOf(senderId)});

        if (cursor != null && cursor.moveToFirst()) {
            try {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                    int sender = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SENDER_ID));
                    int receiver = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_RECEIVER_ID));
                    String messageText = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MESSAGE));
                    String timestamp = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIMESTAMP));

                    Message message = new Message(id, sender, receiver, messageText, timestamp);
                    chatList.add(message);

                } while (cursor.moveToNext());
            } catch (IllegalArgumentException e) {
                Log.e("MessageDB", "Column not found", e);
            } finally {
                cursor.close();
            }
        }
        return chatList;
    }

    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            try {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                    int sender = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SENDER_ID));
                    int receiver = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_RECEIVER_ID));
                    String messageText = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MESSAGE));
                    String timestamp = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIMESTAMP));

                    Message message = new Message(id, sender, receiver, messageText, timestamp);
                    messages.add(message);
                } while (cursor.moveToNext());
            } catch (IllegalArgumentException e) {
                Log.e("MessageDB", "Column not found", e);
            } finally {
                cursor.close();
            }
        }
        return messages;
    }

}
