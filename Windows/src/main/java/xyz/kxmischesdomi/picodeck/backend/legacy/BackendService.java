package xyz.kxmischesdomi.picodeck.backend.legacy;

import xyz.kxmischesdomi.picodeck.backend.legacy.service.IBackendService;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class BackendService implements IBackendService {

	private static BackendService instance;

	private final DeviceHolder deviceHolder;

	public BackendService() {
		deviceHolder = new DeviceHolder();
	}

	public void onTerminate() {
		deviceHolder.onTerminate();
	}

	public DeviceHolder getDeviceHolder() {
		return deviceHolder;
	}

	public static BackendService getInstance() {
		return instance;
	}

	public static void setInstance(BackendService instance) {
		BackendService.instance = instance;
	}

}
