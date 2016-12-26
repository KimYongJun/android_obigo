package com.obigo.obigoproject.service;

/**
 * Created by O BI HE ROCK on 2016-12-07
 * 김용준, 최현욱
 * ServiceManager
 */

public enum ServiceManager {
    INSTANCE;
    // 유저 서비스
    private UserService userService;
    // 차량 서비스
    private UserVehicleService userVehicleService;
    // 요청 서비스
    private UserRequestService userRequestService;
    // 메시지 서비스
    private MessageService messageService;
    // 번들 서비스
    private BundleService bundleService;

    ServiceManager() {
        userService = RetrofitServiceGenericFactory.createService(UserService.class);
        userVehicleService = RetrofitServiceGenericFactory.createService(UserVehicleService.class);
        userRequestService = RetrofitServiceGenericFactory.createService(UserRequestService.class);
        messageService = RetrofitServiceGenericFactory.createService(MessageService.class);
        bundleService = RetrofitServiceGenericFactory.createService(BundleService.class);
    }

    public UserService getUserService() {
        return userService;
    }

    public UserVehicleService getUserVehicleService() {
        return userVehicleService;
    }

    public UserRequestService getUserRequestService() {
        return userRequestService;
    }

    public MessageService getMessageService() {
        return messageService;
    }

    public BundleService getBundleService(){return  bundleService;}

    public static ServiceManager getInstance() {
        return INSTANCE;
    }
}
