package com.xuaxi.service;

import com.xuaxi.framework.core.server.IBaseService;
import com.xuaxi.domain.InvoiceDomain;
import com.xuaxi.entity.InvoiceEntity;

public interface IInvoiceService extends IBaseService<InvoiceDomain, InvoiceEntity, Long>{

	int superMerge(InvoiceDomain domain);
}
