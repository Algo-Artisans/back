package com.example.AA.repository;

import com.example.AA.dto.PortfolioResDto;
import com.example.AA.dto.PortfolioSearchCondition;
import com.example.AA.dto.QPortfolioResDto;
import com.example.AA.entity.QHairStyle;
import com.example.AA.entity.QPortfolio;
import com.example.AA.entity.QPortfolioHairStyle;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.util.List;


// 검색 저장소
@Repository
public class PortfolioSearchRepository {

    private final EntityManager em;

    private final JPAQueryFactory queryFactory;

    public PortfolioSearchRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

//   public List<PortfolioResDto> filter(PortfolioSearchCondition portfolioSearchCondition){
//       String hairName = portfolioSearchCondition.getHairname();
//
//       QPortfolio portfolio = QPortfolio.portfolio;
//       QPortfolioHairStyle portfolioHairStyle = QPortfolioHairStyle.portfolioHairStyle;
//       QHairStyle hairStyle = QHairStyle.hairStyle;
//
//       Predicate predicate = null;
//       if (hairName != null) {
//           predicate = hairStyle.hairName.eq(hairName);
//       }
//
//       return queryFactory
//               .selectDistinct(new QPortfolioResDto(
//                       portfolio.user,
//                       portfolio.portfolioId,
//                       portfolio.gender,
//                       portfolio.phoneNumber,
//                       portfolio.workplace,
//                       portfolio.snsAddress,
//                       portfolio.introduction,
//                       portfolio.likesCount,
//                       portfolio.profileURL,
//                       portfolio.workImages.any().imageUrl1,
//                       portfolio.workImages.any().imageUrl2,
//                       portfolio.workImages.any().imageUrl3,
//                       portfolio.workImages.any().imageUrl4,
//                       hairStyle.hairName.stringValue(), // hairStyle의 hairName을 문자열로 변환하여 할당
//                       hairStyle.faceShape // 필요한 경우 faceShape도 가져올 수 있습니다
//               ))
//               .from(portfolio)
//               .leftJoin(portfolio.portfolioHairStyles, portfolioHairStyle)
//               .leftJoin(portfolioHairStyle.hairStyle, hairStyle)
//               .where(predicate)
//               .fetch();
//   }

}
