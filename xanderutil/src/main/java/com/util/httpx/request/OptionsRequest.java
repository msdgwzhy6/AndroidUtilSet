package com.util.httpx.request;


import com.util.core.xhttp.model.HttpHeaders;
import com.util.core.xhttp.utils.HttpUtils;
import com.util.core.xhttp.utils.JJLogger;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * ================================================
 * 描    述：Options请求
 * 修订历史：
 * ================================================
 */
public class OptionsRequest extends BaseBodyRequest<OptionsRequest> {

    public OptionsRequest(String url) {
        super(url);
        method = "OPTIONS";
    }

    @Override
    public Request generateRequest(RequestBody requestBody) {
        try {
            headers.put(HttpHeaders.HEAD_KEY_CONTENT_LENGTH, String.valueOf(requestBody.contentLength()));
        } catch (IOException e) {
            JJLogger.e(e);
        }
        Request.Builder requestBuilder = HttpUtils.appendHeaders(headers);
        return requestBuilder.method("OPTIONS", requestBody).url(url).tag(tag).build();
    }
}
