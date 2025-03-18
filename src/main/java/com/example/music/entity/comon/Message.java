package com.example.music.entity.comon;

public enum Message {

    // Account and user
    ACCOUNT_NOT_EXISTS("ERR001","Account does not exist!"),
    ACCOUNT_ALREADY_EXISTS("ERR048","Account already exists!"),
    INVALID_PASSWORD("ERR002", "Incorrect password!"),
    EMAIL_USER_EXIST("ERR003","User name already exists!"),
    INVALID_USERNAME("ERR004", "Invalid username!"),
    INVALID_DATE_OF_BIRTH("ERR005", "Invalid date of birth!"),
    INVALID_EMAIL("ERR006", "Invalid email!"),
    UNABLE_TO_CREATE_ACCOUNT("ERR007", "Unable to create account!"),
    CANNOT_LOG_IN("ERR008", "Cannot log in!"),
    UNABLE_TO_VERIFY_INFORMATION("ERR009", "Unable to verify information!"),
    INVALID_PERMISSION("ERR010", "Invalid permission!"),
    CANNOT_ADD_NEW_USER("ERR011", "Cannot add new user!"),
    CANNOT_UPDATE_NEW_USER("ERR012", "Cannot update new user!"),
    SYSTEM_ERROR("ERR013", "System error occurred!"),
    ERROR_WHILE_GETTING_USER_LIST("ERR014", "Error while getting user list!"),
    ERROR_WHEN_GETTING_AUTHOR_DATA_TO_FILL_IN_SELECT_BOX("ERR015", "Error when getting author data to fill in select box!"),
    ERROR_WHILE_GETTING_NEW_USER_LIST("ERR016", "Error while getting new user list!"),
    ERROR_GETTING_AUTHOR_LIST("ERR017", "Error getting author list!"),
    ERROR_RETRIEVING_USER_DETAILS("ERR018", "Error retrieving user details!"),
    UNABLE_TO_UPDATE_USER_STATUS("ERR019", "Unable to update user status!"),
    INVALID_GENDER("ERR020", "Invalid gender!"),
    INFORMATION_CANNOT_BE_LEFT_BLANK("ERR047", "Information cannot be left blank!"),

    // Song
    CANNOT_CREATE_NEW_SONG("ERR021", "Cannot create new song!"),
    MUSIC_GENRE_DOES_NOT_EXIST("ERR022", "Music genre does not exist!"),
    AUTHOR_DOES_NOT_EXIST("ERR023", "Author does not exist!"),
    SONG_NAME_IS_BLANK("ERR024", "Song name cannot blank!"),
    PHOTO_CANNOT_BE_BLANK("ERR025", "Photo cannot be blank!"),
    AUDIO_FILE_CANNOT_BE_BLANK("ERR026", "Audio file cannot be blank!"),
    INVALID_SONG_DURATION("ERR027", "Invalid song duration!"),
    CANNOT_UPDATE_SONG("ERR028", "Cannot update song!"),
    CANNOT_UPDATE_STATUS("ERR029", "Cannot change status!"),
    SONG_DOES_NOT_EXIST("ERR030", "Song does not exist!"),
    ERROR_WHILE_RETRIEVING_SONG_LIST("ERR031", "Error while retrieving song list!"),
    ERROR_WHILE_SEARCHING_FOR_SONGS("ERR032", "Error while searching for songs!"),

    // Genres
    INVALID_MUSIC_GENRE_CODE("ERR033", "Invalid music genres code!"),
    MUSIC_GENRE_NAME_CANNOT_BE_BLANK("ERR034", "Music genres name cannot be blank!"),
    LIST_IS_EMPTY("ERR035", "List is empty!"),
    ERROR_WHILE_RETRIEVING_GENRES_LIST("ERR036", "Error while retrieving genres list!"),
    GENRES_DOES_NOT_EXIST("ERR037", "Genres does not exist!"),
    INVALID_MUSIC_GENRES_NAME("ERR038", "Invalid music genres name!"),
    CANNOT_CREATE_NEW_GENRES("ERR039", "Cannot create new genres!"),
    CANNOT_UPDATE_GENRES("ERR040", "Cannot update genres!"),
    ERROR_WHILE_SEARCHING_FOR_GENRES("ERR041", "Error while searching for genres!"),

    // Album
    ERROR_WHEN_GETTING_ALBUM_DATA_FOR_SELECT_CELL("ERR042", "Error when getting album data for select cell!"),
    ERROR_WHILE_ADDING_NEW_ALBUM("ERR043", "Error while adding new album!"),
    ERROR_WHILE_UPDATING_ALBUM("ERR044", "Error while updating album!"),
    ERROR_WHILE_CHANGING_ALBUM_STATUS("ERR045", "Error while changing album status!"),
    ALBUM_DOES_NOT_EXIST("ERR046", "Album does not exist!");


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
