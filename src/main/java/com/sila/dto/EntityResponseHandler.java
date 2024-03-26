package com.sila.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class EntityResponseHandler<T> {
  private List<T> contents;
  private int page;
  private int pageSize;
  private int totalPages;
  private long total;
  private boolean hasNext;
  private int totalInvalid;

  public EntityResponseHandler(Page<T> page) {
    this(
        page.getContent(),
        page.getNumber() + 1,
        page.getSize(),
        page.getTotalPages(),
        page.getTotalElements(),
        page.hasNext());
  }

  public EntityResponseHandler(List<T> list) {
    this(list, 0, 0, 0, list.size(), false);
  }

  public EntityResponseHandler(
      List<T> contents, int page, int pageSize, int totalPages, long total, boolean hasNext) {
    this.contents = new ArrayList<>(contents);
    this.page = page;
    this.totalPages = totalPages;
    this.pageSize = pageSize;
    this.total = total;
    this.hasNext = hasNext;
  }

  public EntityResponseHandler(
      List<T> contents, int page, int pageSize, long total, int totalInvalid) {
    this.contents = new ArrayList<>(contents);
    this.page = page + 1;
    this.total = total;
    this.pageSize = pageSize;
    this.totalPages = pageSize == 0 ? 1 : (int) Math.ceil((double) total / (double) pageSize);
    this.hasNext = page + 1 < totalPages;
    this.totalInvalid = totalInvalid;
  }
}

