package org.wordpress.android.models;

import org.wordpress.android.fluxc.model.CommentModel;
import org.wordpress.android.fluxc.model.CommentStatus;

import java.util.ArrayList;

public class CommentList extends ArrayList<CommentModel> {
    public int indexOfCommentId(long commentId) {
        for (int i = 0; i < this.size(); i++) {
            if (commentId == this.get(i).getRemoteCommentId()) {
                return i;
            }
        }
        return -1;
    }

    /*
     * replace comments in this list that match the passed list
     */
    public void replaceComments(final CommentList comments) {
        if (comments == null || comments.size() == 0) {
            return;
        }
        for (CommentModel comment : comments) {
            int index = indexOfCommentId(comment.getRemoteCommentId());
            if (index > -1) {
                set(index, comment);
            }
        }
    }

    /*
     * delete comments in this list that match the passed list
     */
    public void deleteComments(final CommentList comments) {
        if (comments == null || comments.size() == 0) {
            return;
        }
        for (CommentModel comment : comments) {
            int index = indexOfCommentId(comment.getRemoteCommentId());
            if (index > -1) {
                remove(index);
            }
        }
    }

    /*
     * returns true if any comments in this list have the passed status
     */
    public boolean hasAnyWithStatus(CommentStatus status) {
        for (CommentModel comment : this) {
            if (status.toString().equals(comment.getStatus())) {
                return true;
            }
        }
        return false;
    }

    /*
     * returns true if any comments in this list do NOT have the passed status
     */
    public boolean hasAnyWithoutStatus(CommentStatus status) {
        for (CommentModel comment : this) {
            if (!status.toString().equals(comment.getStatus())) {
                return true;
            }
        }
        return false;
    }

    /*
     * does passed list contain the same comments as this list?
     */
    public boolean isSameList(CommentList comments) {
        if (comments == null || comments.size() != this.size()) {
            return false;
        }

        for (final CommentModel comment : comments) {
            int index = this.indexOfCommentId(comment.getRemoteCommentId());
            if (index == -1) {
                return false;
            }
            final CommentModel thisComment = this.get(index);
            if (!thisComment.getStatus().equals(comment.getStatus())) {
                return false;
            }
            if (!thisComment.getContent().equals(comment.getContent())) {
                return false;
            }
            if (!thisComment.getAuthorName().equals(comment.getAuthorName())) {
                return false;
            }
            if (!thisComment.getAuthorEmail().equals(comment.getAuthorEmail())) {
                return false;
            }
            if (!thisComment.getAuthorUrl().equals(comment.getAuthorUrl())) {
                return false;
            }
        }

        return true;
    }
}
