package edu.miu.cs.librarysystem.service;

import edu.miu.cs.librarysystem.business.LibraryMember;
import edu.miu.cs.librarysystem.dataaccess.DataAccess;
import edu.miu.cs.librarysystem.dataaccess.DataAccessFacade;
import java.util.List;
import java.util.Optional;

public class LibraryMemberService {

  private static final LibraryMemberService INSTANCE = new LibraryMemberService();
  private final DataAccess DA = new DataAccessFacade();

  private LibraryMemberService() {}

  public static LibraryMemberService getInstance() {
    return INSTANCE;
  }

  public List<LibraryMember> getAllMembers() {
    return DA.readMemberMap().values().stream().toList();
  }

  public Optional<LibraryMember> findMemberById(String memberId) {
    return DA.findMemberById(memberId);
  }

  public LibraryMember save(LibraryMember member) {
    return DA.saveNewMember(member);
  }

  public LibraryMember deleteMember(String memberId) {
    return DA.deleteMember(memberId);
  }
}
