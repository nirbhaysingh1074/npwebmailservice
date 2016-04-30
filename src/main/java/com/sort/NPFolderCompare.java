package com.sort;

import java.util.Comparator;

import com.webmail.folder.MailImapFolders;



public class NPFolderCompare implements Comparator<MailImapFolders> {

@Override
public int compare(MailImapFolders obj1, MailImapFolders obj2) {
    return (obj1.getFolderFullName()).compareTo((obj2.getFolderFullName()));
}

}
