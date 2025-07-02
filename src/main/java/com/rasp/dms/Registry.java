package com.rasp.dms;
import platform.helper.HelperManager;
import platform.webservice.ServiceManager;
import com.rasp.dms.helper.*;
import com.rasp.dms.service.*;
public class Registry {
		public static void register(){
				 HelperManager.getInstance().register(DocumentHelper.getInstance());
//				 ServiceManager.getInstance().register(new DocumentService());
		}
}
