package org.ztemplates.actions.security;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ZRoles
{
  private boolean secure = false;

  private boolean impossible = false;

  private Set<String> roles = new HashSet<String>();


  public void add(String[] r)
  {
    if (r.length > 0)
    {
      secure = true;
      if (roles.isEmpty())
      {
        Collections.addAll(roles, r);
      }
      else
      {
        Collection<String> crt = new HashSet<String>();
        Collections.addAll(crt, r);
        roles.retainAll(crt);
        if (roles.isEmpty())
        {
          impossible = true;
        }
      }
    }
  }


  public String toString()
  {
    return roles.toString();
  }


  public boolean isSecure()
  {
    return secure;
  }


  public boolean isImpossible()
  {
    return impossible;
  }


  public Set<String> getRoles()
  {
    return roles;
  }
}
