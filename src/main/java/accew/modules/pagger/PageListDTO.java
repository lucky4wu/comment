package accew.modules.pagger;

import java.io.Serializable;
import java.util.List;

public class PageListDTO<T> implements Serializable {
	private List<T> pageList;
	private long pageCount;
	private long totalRows;
	private long currentPage = 1;
	private long pageSize = 50;
	private long perPageSize = 50;

	public PageListDTO() {
	}

	public PageListDTO(Long totalRows) {
		this.setTotalRows(totalRows);
	}

	public PageListDTO(Long page, Long totalRows) {
		this.setCurrentPage(page);
		this.setTotalRows(totalRows);
	}

	public PageListDTO(Long page, Long totalRows, Long perPageSize) {
		this.setCurrentPage(page);
		this.setTotalRows(totalRows);
		this.setPerPageSize(perPageSize);
	}

	public long getPerPageSize() {
		return perPageSize;
	}

	public void setPerPageSize(long perPageSize) {
		this.perPageSize = perPageSize;
	}

	public Long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Long currentPage) {
		this.currentPage = currentPage;
	}

	public Long getPageCount() {
		return this.pageCount;
	}

	public void setPageCount(Long pageCount) {
		this.pageCount = pageCount;
	}

	public List<T> getPageList() {
		return pageList;
	}

	public void setPageList(List<T> pageList) {
		this.pageList = pageList;
	}

	public Long getPageSize() {
		return pageSize;
	}

	public void setPageSize(Long pageSize) {
		this.pageSize = pageSize;
	}

	public Long getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(Long totalRows) {
		this.totalRows = totalRows;
	}

	public Long getNextPage() {
		if ((this.getPageCount() - this.getCurrentPage()) > 0) {
			return currentPage + 1;
		}
		else {
			return currentPage;
		}
	}

	public Long getPrevPage() {
		if ((this.getCurrentPage() - 1) > 0) {
			return this.getCurrentPage() - 1;
		}
		else {
			return this.getCurrentPage();
		}
	}


}
