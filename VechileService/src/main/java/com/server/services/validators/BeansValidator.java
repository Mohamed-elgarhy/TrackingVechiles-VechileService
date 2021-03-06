package com.server.services.validators;

import com.server.services.VechileBean;
import com.server.services.constants.ValidatorConstants;

/**
 * @author mkader3
 *
 *This class handles beans validation centrally to avoid code repeating 
 *
 * can use bean validator JSR but need more time
 *
 */
public class BeansValidator {

	private BeansValidator() {
		// TODO Auto-generated constructor stub
	}
	public static Short validateVechileBeanForStatusUpdate(VechileBean vechileBean)
	{
		if (vechileBean != null )
		{
			if (vechileBean.getVechileId()!=null && !"".equalsIgnoreCase(vechileBean.getVechileId()))
			{
				if (vechileBean.getStatus()!=null)
				{
					return ValidatorConstants.VALID_BEAN;
				}
				else
				{
					return ValidatorConstants.EMPTY_STATUS_NOT_ALLOWED;
				}
			}
			
			else {
				return ValidatorConstants.EMPTY_VECHILE_ID_NOT_ALLOWED;
			}
		}
		else
			return ValidatorConstants.EMPTY_VECHILE_ID_NOT_ALLOWED;
		
	}
	
}
