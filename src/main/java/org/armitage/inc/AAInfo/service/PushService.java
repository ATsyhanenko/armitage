package org.armitage.inc.AAInfo.service;

import org.armitage.inc.AAInfo.entity.User;
import org.armitage.inc.AAInfo.extra.PushMessageDto;

public interface PushService {

	void sendPushMessage(User user, String message);

}
