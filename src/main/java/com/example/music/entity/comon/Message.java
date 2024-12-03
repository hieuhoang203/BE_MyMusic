package com.example.music.entity.comon;

public enum Message {

    NOT_EXISTS("ERR001","Account does not exist!"),
    PASSWORD_NOT_EXISTS("ERR002", "Incorrect password!"),
    EMAIL_USER_EXIST("ERR003","User name already exists!"),
    CANNOT_CREATE_NEW_SONG("ERR004", "Cannot create new song!"),
    MUSIC_GENRE_DOES_NOT_EXIST("ERR005", "Music genre does not exist!"),
    AUTHOR_DOES_NOT_EXIST("ERR006", "Author does not exist!"),
    SONG_NAME_IS_BLANK("ERR007", "Song name cannot blank!"),
    PHOTO_CANNOT_BE_BLANK("ERR008", "Photo cannot be blank!"),
    AUDIO_FILE_CANNOT_BE_BLANK("ERR009", "Audio file cannot be blank!"),
    INVALID_SONG_DURATION("ERR010", "Invalid song duration!"),
    INVALID_MUSIC_GENRE_CODE("ERR011", "Invalid music genre code!"),
    MUSIC_GENRE_NAME_CANNOT_BE_BLANK("ERR012", "Music genre name cannot be blank!"),
    INVALID_USERNAME("ERR013", "Invalid username!"),
    INVALID_DATE_OF_BIRTH("ERR014", "Invalid date of birth!"),
    INVALID_EMAIL("ERR015", "Invalid email!"),
    CANNOT_UPDATE_SONG("ERR016", "Cannot update song!"),
    CANNOT_UPDATE_STATUS("ERR017", "Cannot change status!"),
    SONG_DOES_NOT_EXIST("ERR018", "Song does not exist!"),
    LIST_IS_EMPTY("ERR019", "List is empty!"),
    ERROR_WHILE_RETRIEVING_SONG_LIST("ERR020", "Error while retrieving song list!"),
    ERROR_WHILE_SEARCHING_FOR_SONGS("ERR021", "Error while searching for songs!"),
    ERROR_WHILE_RETRIEVING_GENRES_LIST("ERR022", "Error while retrieving genres list!"),
    GENRE_DOES_NOT_EXIST("ERR023", "Unable to verify information!"),
    UNABLE_TO_VERIFY_INFORMATION("ERR024", "Unable to verify information!"),
    INVALID_MUSIC_GENRE_NAME("ERR025", "Invalid music genre name!"),
    CANNOT_CREATE_NEW_GENRE("ERR026", "Cannot create new genre!"),
    CANNOT_UPDATE_GENRE("ERR027", "Cannot update genre!"),
    ERROR_WHILE_SEARCHING_FOR_GENRES("ERR028", "Error while searching for genres!"),
    INVALID_GENDER("ERR029", "Invalid gender!"),
    INVALID_PERMISSION("ERR030", "Invalid permission!"),
    CANNOT_ADD_NEW_USER("ERR031", "Cannot add new user!"),
    CANNOT_UPDATE_NEW_USER("ERR032", "Cannot update new user!");

    private String code;

    private String message;

    Message(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return this.code;
    }

}
