package com.dajia.mobile.android.framework.component.onActivityForResult;

import android.content.Intent;

/**
 * OnActivityForResultCallback
 *
 * @author hewanxian
 * @date 16/4/5
 * Copyright © 2016年 branch.6.3.0.portal.3.2.AppBox.TabStyle. All rights reserved.
 */
public interface OnActivityForResultCallback {
    void result(Integer resultCode, Intent data);
    void success(Integer resultCode, Intent data);
    void cancel(Intent data);
}
