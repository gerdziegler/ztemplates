package org.ztemplates.web.request;


/**
 * The request-time environment
 * 
 * @author gerdziegler.de
 * 
 */
public interface ZIServiceRepository extends ZICoreServiceRepository, ZIApplicationServiceRepository
{


  // /**
  // * user defined services, create in ZIServiceFactory, add ContextListener to
  // * your webapp
  // *
  // * <code>
  // ZIApplicationRepository applicationRepository = new
  // ZApplicationRepository(req.getSession().getServletContext());
  // ZApplication application = applicationRepository.getApplication();
  // </code>
  // *
  // *
  // * @param <T>
  // * @param type
  // * @return
  // * @throws Exception
  // */
  // public <T extends ZIService> T getService(Class<T> type) throws Exception;
}
