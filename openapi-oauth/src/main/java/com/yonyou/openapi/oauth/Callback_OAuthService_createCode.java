// **********************************************************************
//
// Copyright (c) 2003-2015 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************
//
// Ice version 3.6.1
//
// <auto-generated>
//
// Generated from file `OAuth.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package com.yonyou.openapi.oauth;

public abstract class Callback_OAuthService_createCode
    extends IceInternal.TwowayCallback implements Ice.TwowayCallbackArg1UE<String>
{
    public final void __completed(Ice.AsyncResult __result)
    {
        OAuthServicePrxHelper.__createCode_completed(this, __result);
    }
}
