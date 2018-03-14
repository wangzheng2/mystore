package com.cskaoyan.test;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*import javax.enterprise.inject.New;*/

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.junit.Test;

public class DateTest {

	@Test
	public void TestDateConvert() throws ParseException{
		
		String dateString ="1990/02/01"; //1990/2/1  
 		
		String s="Sat, 12 Aug 1995 13:30:00 GMT";  //
		
		String dateString2 ="1990-2-1";  //

		String dateString3 ="19900201"; // Dec 31 00:00:00 CST 1989

		DateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd hh：mm：ss"); //表示月份的MM一定大写 其他的小写

		//Date date = new Date(dateString3);
		
		
		String format = dateFormat.format(new Date());
		System.out.println("DateTest.TestDateConvert()"+format);
		
		String dateString4 ="2017-07-30 13：33：45"; // Dec 31 00:00:00 CST 1989

		
		Date date = dateFormat.parse(dateString4);

		int month = date.getMonth();  //1月是0
		int day = date.getDate();
		int weekday = date.getDay();  //周一是0 
		
		
		System.out.println("DateTest.TestDateConvert()"+date);
		System.out.println("DateTest.TestDateConvert()"+month);
		System.out.println("DateTest.TestDateConvert()"+day);
		System.out.println("DateTest.TestDateConvert()"+weekday);

		//
	}
	
	@Test
	public void beanTest(){
		
		    String bd = "1991-01-01";  
	        Person p = new Person();  
	        Person p2 = new Person();  

	        try {

				DateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd");
			    Date parse = dateFormat.parse(bd);
			    Date parse2 =new Date();
				  
				  
				BeanUtils.setProperty(p, "name", "zhangxx");
				BeanUtils.setProperty(p, "birthday",parse);

				ConvertUtils.register(new DateLocaleConverter(), Date.class);
				BeanUtils.copyProperties(p2, p);
				
				System.out.println("DateTest.beanTest()"+p);
				System.out.println("DateTest.beanTest()"+p2);

			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	              //  System.out.println(p);
		    catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
			}
			 
	        
	} 
} 


 