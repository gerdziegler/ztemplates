package org.ztemplates.web.script.experimental.repo.impl;

import java.util.Comparator;

public class ZDefaultVersionComparator implements Comparator<String>
{
  public int compare(String s1, String s2)
  {
    int idxFirst1 = s1.indexOf('.');
    int idxFirst2 = s2.indexOf('.');

    int major1 = idxFirst1 < 0 ? Integer.parseInt(s1) : Integer.parseInt(s1.substring(0, idxFirst1));
    int major2 = idxFirst2 < 0 ? Integer.parseInt(s2) : Integer.parseInt(s2.substring(0, idxFirst2));

    if (major1 < major2)
    {
      return -1;
    }
    else if (major1 > major2)
    {
      return 1;
    }
    else if (idxFirst1 > 0 && idxFirst2 > 0)
    {
      return compare(s1.substring(idxFirst1 + 1), s2.substring(idxFirst2 + 1));
    }
    else if (idxFirst1 < 0 && idxFirst2 < 0)
    {
      return 0;
    }
    else if (idxFirst1 < 0) // && idx2First>0
    {
      return -1;
    }
    else
    // (idxFirst1 > 0) && idx2First<0
    {
      return 1;
    }

    // int idxLast1 = idxFirst1 < 0 ? -1 : s1.indexOf('.', idxFirst1 + 1);
    // int idxLast2 = idxFirst2 < 0 ? -1 : s2.indexOf('.', idxFirst2 + 1);
    //
    // int minor1 = -1;
    // int minor2 = -1;
    //
    // if( minor1 = (Integer.parseInt(idxLast1 > 0 ? s1.substring(idxFirst1 + 1,
    // idxLast1) : ));
    // minor2 = (Integer.parseInt(idxLast2 > 0 ? s2.substring(idxFirst2 + 1,
    // idxLast2) : ))
    // {
    // ;
    // }
    // if (minor1 < minor2)
    // {
    // return 1;
    // }
    // if (minor1 > minor2)
    // {
    // return -1;
    // }
    //
    // int rev1 = idxLast1 < 0 ? -1 : Integer.parseInt(s1.substring(idxLast1 +
    // 1));
    // int rev2 = idxLast2 < 0 ? -1 : Integer.parseInt(s2.substring(idxLast2 +
    // 1));
    // if (rev1 < rev2)
    // {
    // return 1;
    // }
    // if (rev1 > rev2)
    // {
    // return -1;
    // }
    //
    // return 0;
  }
}
