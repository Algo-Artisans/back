package com.example.AA.repository;

import com.example.AA.dto.PortfolioResDto;
import com.example.AA.dto.PortfolioSearchCondition;
import com.example.AA.dto.QPortfolioResDto;
import com.example.AA.entity.QHairStyle;
import com.example.AA.entity.QPortfolio;
import com.example.AA.entity.QPortfolioHairStyle;
import com.example.AA.entity.QWorkImage;
import com.example.AA.entity.enumtype.HairName;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

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

//    public List<PortfolioResDto> searchHairname(String hairName) {
//        QPortfolio portfolio = QPortfolio.portfolio;
//        QPortfolioHairStyle portfolioHairStyle = QPortfolioHairStyle.portfolioHairStyle;
//        QHairStyle hairStyle = QHairStyle.hairStyle;
//        QWorkImage workImage = QWorkImage.workImage;
//
//        Predicate predicate = null;
//        if (!StringUtils.isEmpty(hairName)) {
//            predicate = hairStyle.hairName.eq(HairName.valueOf(hairName));
//        }

//        return queryFactory
//                .selectDistinct(new QPortfolioResDto(
//                        portfolio.user,
//                        portfolio,
//                        workImage,
//                        ExpressionUtils.as(
//                                JPAExpressions
//                                        .select(hairStyle.hairName)
//                                        .from(portfolioHairStyle)
//                                        .join(portfolioHairStyle.hairStyle, hairStyle)
//                                        .where(portfolioHairStyle.portfolio.eq(portfolio))
//                                        .orderBy(portfolioHairStyle.id.asc())
//                                        .limit(1),
//                                "hairStyle1"
//                        ),
//                        ExpressionUtils.as(
//                                JPAExpressions
//                                        .select(hairStyle.hairName)
//                                        .from(portfolioHairStyle)
//                                        .join(portfolioHairStyle.hairStyle, hairStyle)
//                                        .where(portfolioHairStyle.portfolio.eq(portfolio))
//                                        .orderBy(portfolioHairStyle.id.asc())
//                                        .limit(1, 1),
//                                "hairStyle2"
//                        ),
//                        ExpressionUtils.as(
//                                JPAExpressions
//                                        .select(hairStyle.hairName)
//                                        .from(portfolioHairStyle)
//                                        .join(portfolioHairStyle.hairStyle, hairStyle)
//                                        .where(portfolioHairStyle.portfolio.eq(portfolio))
//                                        .orderBy(portfolioHairStyle.id.asc())
//                                        .limit(1, 2),
//                                "hairStyle3"
//                        )
//                ))
//                .from(portfolio)
//                .leftJoin(portfolio, portfolioHairStyle.portfolio)
//                .leftJoin(portfolioHairStyle.hairStyle, hairStyle)
//                .leftJoin(portfolio, workImage.portfolio)
//                .where(predicate)
//                .fetch();
// }


}

